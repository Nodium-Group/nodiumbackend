package nodium.group.backend.security.manager;

import lombok.AllArgsConstructor;
import nodium.group.backend.security.provider.BackendAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS_PROVIDED;

@Component
@AllArgsConstructor
public class BackendAuthManager implements AuthenticationManager {
    @Autowired
    private BackendAuthProvider provider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(provider.supports(authentication.getClass()))
            return provider.authenticate(authentication);
        throw new BadCredentialsException(INVALID_DETAILS_PROVIDED.getMessage());
    }
}
