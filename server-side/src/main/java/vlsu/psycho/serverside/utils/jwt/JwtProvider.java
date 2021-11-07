package vlsu.psycho.serverside.utils.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.config.ApplicationProperties;
import vlsu.psycho.serverside.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final ApplicationProperties properties;
    private static final Map<Claim, Function<User, Object>> CLAIMS_FOR_JWT = Map.of(
            Claim.EXTERNAL_ID, User::getExternalId,
            Claim.EMAIL, User::getEmail
    );

    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(setClaims(user))
                .setSubject(user.getLogin())
                .setExpiration(new Date(new Date().getTime() + properties.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
                .compact();
    }

    private Map<String, Object> setClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        properties.getClaimForJwt().forEach(
                claim -> claims.put(claim.toString(), CLAIMS_FOR_JWT.get(claim).apply(user))
        );
        return claims;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(properties.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(properties.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public Object getClaimFromToken(Claim claim) {
        String token = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        return Jwts.parser().setSigningKey(properties.getJwtSecret()).parseClaimsJws(token).getBody().get(claim.toString());
    }
}
