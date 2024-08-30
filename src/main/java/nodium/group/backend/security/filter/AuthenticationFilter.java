package nodium.group.backend.security.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
}
