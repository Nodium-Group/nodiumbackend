package nodium.group.backend.security.middlewares;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!Arrays.stream(PUBLIC_END_POINTS).anyMatch(r->r.equals(request.getRequestURI()))){
            log.info("Authorization starts");
            String token = request.getHeader(AUTHORIZATION);
            if(isValid(token))
                verifyToken(token);
        }
        filterChain.doFilter(request,response);
    }
    public boolean isValid(String token){
        if(token.startsWith(AUTH_HEADER_PREFIX))
            token = token.substring(AUTH_HEADER_PREFIX.length()).strip();
       Optional<Tokens> tokens =  repository.findByToken(token);
       if(tokens.isEmpty()){
            log.info("token given is invalid");
           return false;
       }
        verifyToken(token);
        log.info("verified token received");
        return true;
    }
    private void verifyToken(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .withIssuer(APP_NAME)
                .withClaimPresence("principal")
                .withClaimPresence("credentials")
                .withClaimPresence("roles")
                .build().verify(token);
        List<? extends SimpleGrantedAuthority> grantedAuthorities = decodedJWT.getClaim("roles")
                .asList(SimpleGrantedAuthority.class);
        String principal = decodedJWT.getClaim("principal").toString();
        String credentials = decodedJWT.getClaim("credentials").toString();
        Authentication auth = new UsernamePasswordAuthenticationToken(principal,credentials,grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
