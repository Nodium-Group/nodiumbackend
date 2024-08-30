package nodium.group.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.security.manager.BackendAuthManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final BackendAuthManager manager;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            InputStream inputStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
            var result = manager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(result);
            return result;
        }
        catch(IOException exception){
            throw new BackEndException(SOMETHING_WENT_WRONG.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

    }
}
