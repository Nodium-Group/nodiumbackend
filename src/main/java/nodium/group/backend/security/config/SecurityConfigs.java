package nodium.group.backend.security.config;

import lombok.AllArgsConstructor;
import nodium.group.backend.security.filter.AuthenticationFilter;
import nodium.group.backend.security.filter.AuthourizationFilter;
import nodium.group.backend.security.manager.BackendAuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import static nodium.group.backend.data.enums.Role.PROVIDER;
import static nodium.group.backend.data.enums.Role.USER;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Component
@AllArgsConstructor
public class SecurityConfigs {
    private final AuthourizationFilter authourizationFilter;
    private final BackendAuthManager manager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var authFilter = new AuthenticationFilter();
        authFilter.setFilterProcessesUrl(LOGIN_URL);
        authFilter.setAuthenticationManager(manager);
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .addFilterAt(authFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authourizationFilter, AuthenticationFilter.class)
                .sessionManagement(state->state.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(c->c.requestMatchers(POST,PUBLIC_END_POINTS).permitAll())
                .authorizeHttpRequests(c->c.requestMatchers(POST,USER_END_POINTS).hasAuthority(USER.name()))
                .build();
    }

}
