package nodium.group.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.data.enums.Role;
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
import static nodium.group.backend.utils.AppUtils.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private BackendAuthManager manager;
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
            var result = manager.authenticate(authentication);
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
        String token = generateToken(roles);
        User user = userService.getUserByEmail(authResult.getPrincipal().toString());
        var registerResponse = mapper.map(user, RegisterResponse.class);
        LoginResponse loginResponse = new LoginResponse(registerResponse);
        response.setHeader(AUTHORIZATION, BEARER+token);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.
                writeValueAsBytes(new ApiResponse(true, loginResponse, LocalDateTime.now())));
        response.flushBuffer();
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiResponse apiResponse = new ApiResponse(false,SOMETHING_WENT_WRONG.getMessage(),LocalDateTime.now());
        objectMapper.writeValue(response.getOutputStream(),apiResponse);
        response.flushBuffer();
    }
}
