package vlsu.psycho.serverside.config;

import liquibase.pro.packaged.A;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private Map<ErrorCode, ErrorDefinition> errorMapping;
    private String passwordRegEx;
    private String loginRegEx;
    private String jwtSecret;
    private String emailRegEx;
    private Long expirationTime;
    private String authHeader;
    private List<Claim> claimForJwt;
    private List<UUID> selfUnsatisfactory;
    private List<UUID> lockedInCage;
    private List<UUID> professionalDutiesReduction;
    private List<UUID> emotionalDetachment;
    private List<UUID> selfDetachement;

    @Data
    @Accessors(chain = true)
    public static class ErrorDefinition {
        private String code;
        private String message;
    }
}
