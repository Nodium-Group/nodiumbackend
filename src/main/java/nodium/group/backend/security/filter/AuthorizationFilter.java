package nodium.group.backend.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nodium.group.backend.exception.BackEndException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static nodium.group.backend.exception.ExceptionMessages.RE_LOGIN;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);
        try{
            if (header == null || !header.startsWith(AUTH_HEADER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            boolean isValidHeader= header.startsWith(AUTH_HEADER_PREFIX) &&
                    header.substring(SEVEN).length()>AUTH_HEADER_PREFIX.length();
            if(header!= null && isValidHeader ){
                String token = header.substring(SEVEN).strip();
                if(Optional.of(token).isEmpty()) throw new BackEndException(RE_LOGIN.getMessage());
                JWTVerifier verifier = buildVerifier();
                DecodedJWT decodedJWT = verifier.verify(token);
                List<? extends GrantedAuthority> authorities = decodedJWT.getClaim(ROLES).asList(SimpleGrantedAuthority.class);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(null,null,authorities));
                filterChain.doFilter(request,response);
            }
        }
        catch(Exception exception){
            throw new BackEndException(exception.getMessage());
        }
    }

    private static JWTVerifier buildVerifier() {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .withIssuer(APP_NAME)
                .withClaimPresence(ROLES)
                .build();
    }
}
