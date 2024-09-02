package nodium.group.backend.security.models;

import lombok.AllArgsConstructor;
import nodium.group.backend.data.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
public class SecuredUser implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(user.getRole()).stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
