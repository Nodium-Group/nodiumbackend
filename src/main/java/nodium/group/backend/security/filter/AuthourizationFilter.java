package nodium.group.backend.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static nodium.group.backend.exception.ExceptionMessages.RE_LOGIN;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthourizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestPath = request.getServletPath();
            if (LOGIN_URL.contains(requestPath)) {filterChain.doFilter(request, response);return;}
            String authorization = request.getHeader(AUTHORIZATION);
            if (authorization != null && authorization.startsWith(BEARER)) {
                extractAndSetToken(authorization);
            }
            filterChain.doFilter(request, response);
        }
        catch(Exception exception){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            SecurityContextHolder.clearContext();
            response.getOutputStream().write(RE_LOGIN.getMessage().getBytes());
            response.flushBuffer();        }
    }

    private static void extractAndSetToken(String authorization) {
        String token = authorization.substring(BEARER.length()).strip();
        JWTVerifier verifier = buildVerifier();
        DecodedJWT decodedJWT = verifier.verify(token);
        List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim(ROLES).asList(SimpleGrantedAuthority.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static JWTVerifier buildVerifier() {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .withClaimPresence(ROLES).withIssuer(APP_NAME)
                .build();
    }
}
