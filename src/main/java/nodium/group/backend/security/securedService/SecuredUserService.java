package nodium.group.backend.security.securedService;

import lombok.AllArgsConstructor;
import nodium.group.backend.security.models.SecuredUser;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecuredUserService implements UserDetailsService {
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecuredUser(userService.getUserByEmail(username));
    }
}
