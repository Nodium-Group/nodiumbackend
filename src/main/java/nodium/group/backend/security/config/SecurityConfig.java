package nodium.group.backend.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.transaction.UserTransaction;
import lombok.RequiredArgsConstructor;
import nodium.group.backend.data.repository.TokenRepository;
import nodium.group.backend.security.middlewares.AuthenticationFilter;
import nodium.group.backend.security.middlewares.AuthorizationFilter;
import nodium.group.backend.service.interfaces.UserService;
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
import static org.springframework.http.HttpMethod.POST;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthorizationFilter authorizationFilter;
    private final AuthenticationManager manager;
    private final ObjectMapper mapper;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(manager,mapper,tokenRepository,userService);
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurer()))
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.requestMatchers(POST, PUBLIC_END_POINTS).permitAll()
                                            .requestMatchers("/api/v1/nodium/users/**").hasAuthority(USER.name())
                                            .requestMatchers("/api/v1/nodium/providers/**").hasAuthority(PROVIDER.name())
                                            .anyRequest().authenticated())
                .sessionManagement(c -> c.sessionCreationPolicy(STATELESS))
                .build();
    }
    @Bean
    public CorsFilter corsFilter(){
        CorsConfigurationSource source = corsConfigurer();
        return new CorsFilter(source);
    }
    @Bean
    public CorsConfigurationSource corsConfigurer() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("POST","GET","PATCH","DELETE","OPTIONS","PUT"));
        configuration.setAllowedOrigins(List.of("https://frontend/url","http://localhost:3000"));
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
