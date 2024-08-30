package nodium.group.backend.security.manager;

import lombok.AllArgsConstructor;
import nodium.group.backend.security.provider.BackendAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BackendAuthManager implements AuthenticationManager {
    @Autowired
    private BackendAuthProvider provider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
