package nodium.group.backend.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;

@Component
@RequiredArgsConstructor
public class BackendAuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email= authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(email);
        if(!encoder.matches(password, user.getPassword()))
            throw new BadCredentialsException(INVALID_DETAILS.getMessage());
        return new UsernamePasswordAuthenticationToken(email,password,user.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
