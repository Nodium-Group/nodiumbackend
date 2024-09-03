package nodium.group.backend.security.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.security.filter.AuthenticationFilter;
import nodium.group.backend.security.filter.AuthorizationFilter;
import nodium.group.backend.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import static nodium.group.backend.data.enums.Role.PROVIDER;
import static nodium.group.backend.data.enums.Role.USER;
import static nodium.group.backend.utils.AppUtils.LOGIN_URL;
import static nodium.group.backend.utils.AppUtils.PUBLIC_END_POINTS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig{
    private final AuthenticationManager manager;
    private final AuthorizationFilter authorizationFilter;
    private final ObjectMapper mapper;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var authenticationFilter = new AuthenticationFilter(mapper,manager,userService,modelMapper);
        authenticationFilter.setAuthenticationManager(manager);
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);

        return httpSecurity.
                addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, AuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c->c.requestMatchers(POST,PUBLIC_END_POINTS).permitAll()
                        .requestMatchers("/api/v1/nodium/Users/**").hasAuthority(USER.name())
                        .requestMatchers("/api/v1/providers/**").hasAuthority(PROVIDER.name()))
                .build();
    }
}
