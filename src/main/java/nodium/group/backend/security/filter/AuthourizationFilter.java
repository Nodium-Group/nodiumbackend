package nodium.group.backend.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.security.utils.Utils.extractAndSetToken;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class AuthourizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Integer number = 0;
        try {
            String requestPath = request.getServletPath();
            if (PUBLIC_URLS.contains(requestPath)) {
                filterChain.doFilter(request, response);
                return;
            }
            String authorization = request.getHeader(AUTHORIZATION);
            if (authorization != null && authorization.startsWith(BEARER)) {
                extractAndSetToken(authorization,request,number);
                filterChain.doFilter(request, response);}}
            catch(Exception exception){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            SecurityContextHolder.clearContext();
            response.getOutputStream().write(Arrays.toString(exception.getStackTrace()).getBytes());
            response.flushBuffer();}
    }
}
