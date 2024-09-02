package nodium.group.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.data.models.User;
import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.out.LoginResponse;
import nodium.group.backend.dto.out.RegisterResponse;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.security.manager.BackendAuthManager;
import nodium.group.backend.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.security.utils.Utils.generateToken;
import static nodium.group.backend.utils.AppUtils.AUTH_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(BackendAuthManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }
    private final AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            InputStream inputStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
            var result = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(result);
            return result;
        }
        catch(IOException exception){
            throw new BackEndException(SOMETHING_WENT_WRONG.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> grantedAuthorities = authResult.getAuthorities();
        List<String> roles = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).toList();
        User user = userService.getUserByEmail(authResult.getPrincipal().toString());
        String token = generateToken(roles);
        var registerResponse = mapper.map(user, RegisterResponse.class);
        LoginResponse loginResponse = new LoginResponse(registerResponse);
        response.addHeader(AUTHORIZATION, AUTH_HEADER_PREFIX +token);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                new ApiResponse(true, loginResponse, LocalDateTime.now())));
        response.flushBuffer();
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiResponse apiResponse = new ApiResponse(false,SOMETHING_WENT_WRONG.getMessage(),LocalDateTime.now());
        objectMapper.writeValue(response.getOutputStream(),apiResponse);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.flushBuffer();
    }
}
