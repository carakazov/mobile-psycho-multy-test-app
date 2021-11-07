package vlsu.psycho.serverside.utils.jwt;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@UtilityClass
public class SecurityContextHelper {
    public static UUID getExternalId() {
        return (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
