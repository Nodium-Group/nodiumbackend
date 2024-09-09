package nodium.group.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.utils.AppUtils.AUTH_HEADER_PREFIX;
import static nodium.group.backend.utils.AppUtils.LOGOUT_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class LogoutFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String url = request.getServletPath();
            if (LOGOUT_URL.equals(url)) {
                String token = request.getHeader(AUTHORIZATION).substring(AUTH_HEADER_PREFIX.length());
                tokenService.blacklistToken(token);
                response.getOutputStream().write(new ObjectMapper().writeValueAsString(
                        Map.of("success", true, "message", "Logged out Successfully")).getBytes());
                response.setStatus(OK);
                response.setHeader(AUTHORIZATION, AUTH_HEADER_PREFIX);
                response.flushBuffer();
            }
            filterChain.doFilter(request, response);
        }catch(Exception exception){
            throw new BackEndException(SOMETHING_WENT_WRONG.getMessage());
        }
    }
}

