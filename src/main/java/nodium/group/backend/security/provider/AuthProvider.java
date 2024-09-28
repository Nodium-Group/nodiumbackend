package nodium.group.backend.security.provider;

import lombok.AllArgsConstructor;
import nodium.group.backend.exception.BackEndException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS_PROVIDED;

@Component
@AllArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetails;
    private final PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetails.loadUserByUsername(username);
        if(encoder.matches(password,user.getPassword()))
            return new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        throw new BackEndException(INVALID_DETAILS_PROVIDED.getMessage());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
