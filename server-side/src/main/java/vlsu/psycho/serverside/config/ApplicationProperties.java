package vlsu.psycho.serverside.config;

import liquibase.pro.packaged.A;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;

import java.util.Map;

@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private Map<ErrorCode, ErrorDefinition> errorMapping;
    private String passwordRegEx;
    private String loginRegEx;
    private String jwtSecret;
    private Long expirationTime;
    private String authHeader;

    @Data
    @Accessors
    public static class ErrorDefinition {
        private String code;
        private String message;
    }
}
