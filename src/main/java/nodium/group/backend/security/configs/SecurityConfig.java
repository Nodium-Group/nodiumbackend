package nodium.group.backend.security.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nodium.group.backend.security.filter.AuthenticationFilter;
import nodium.group.backend.security.filter.AuthorizationFilter;
import nodium.group.backend.security.filter.LogoutFilter;
import nodium.group.backend.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static nodium.group.backend.data.enums.Role.PROVIDER;
import static nodium.group.backend.data.enums.Role.USER;
import static nodium.group.backend.utils.AppUtils.*;
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

            return httpSecurity
                    .cors(cors -> cors.configurationSource(corsSource()))
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(c -> c
                            .requestMatchers(POST, PUBLIC_END_POINTS).permitAll()
                            .requestMatchers("/api/v1/nodium/users/**").hasAuthority(USER.name())
                            .requestMatchers("/api/v1/nodium/providers/**").hasAuthority(PROVIDER.name()))
                    .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(new LogoutFilter(), AuthorizationFilter.class)
                    .build();
    }
    @Bean
    public CorsConfigurationSource corsSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}