package nodium.group.backend.security.manager;

import lombok.AllArgsConstructor;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;

@Component
@AllArgsConstructor
public class BackendManager implements AuthenticationManager {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationType =authentication.getClass();
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        comparePasswords(password, email);
//        validateToken(userService.getUserByEmail(email));
        if(authenticationProvider.supports(authenticationType))
            return authenticationProvider.authenticate(authentication);
        throw new BadCredentialsException(INVALID_DETAILS.getMessage());
    }

    private void comparePasswords(String password, String email) {
        if(!encoder.matches(password,userService.getUserByEmail(email).getPassword()))
            throw new BackEndException(INVALID_DETAILS.getMessage());
    }
}
