package nodium.group.backend.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthManager implements AuthenticationManager {
    private final AuthenticationProvider provider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authType = authentication.getClass();
        if(provider.supports(authType))
            return provider.authenticate(authentication);
        throw new ProviderNotFoundException("No Provider found");
    }
}
