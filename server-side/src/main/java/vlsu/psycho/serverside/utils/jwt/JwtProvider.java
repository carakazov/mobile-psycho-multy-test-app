package vlsu.psycho.serverside.utils.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.config.ApplicationProperties;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final ApplicationProperties properties;
    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(new Date().getTime() + properties.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, properties.getJwtSecret())
                .compact();
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
}
