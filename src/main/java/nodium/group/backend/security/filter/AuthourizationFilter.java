package nodium.group.backend.security.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static nodium.group.backend.exception.ExceptionMessages.RE_LOGIN;
import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.security.utils.Utils.buildVerifier;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class AuthourizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();
        try{
                if(PUBLIC_URLS.contains(requestPath)){
                    filterChain.doFilter(request,response);
                    return;
                }
                String authHeader = request.getHeader(AUTHORIZATION);
                if(!PUBLIC_URLS.contains(requestPath) && Optional.of(authHeader.substring(AUTH_HEADER_PREFIX.length()).strip()).isPresent()) {
                    verifyTokenAndSet(authHeader);
                    filterChain.doFilter(request, response);
                }
        }
        catch(Exception exception){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write(RE_LOGIN.getMessage().getBytes());
            response.flushBuffer();
        }
        //        try {
//            String requestPath = request.getServletPath();
//            if (PUBLIC_URLS.contains(requestPath)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            String authorization = request.getHeader(AUTHORIZATION);
//            if (authorization != null && authorization.startsWith(BEARER)) {
//                extractAndSetToken(authorization,request,number);
//                filterChain.doFilter(request, response);}}
//            catch(Exception exception){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            SecurityContextHolder.clearContext();
//            response.getOutputStream().write(Arrays.toString(exception.getStackTrace()).getBytes());
//            response.flushBuffer();
            }

    private void verifyTokenAndSet(String authHeader) {
        JWTVerifier verifier = buildVerifier();
        String token = authHeader.substring(AUTH_HEADER_PREFIX.length()).strip();
        DecodedJWT decodedJWT = verifier.verify(token);
        List<? extends GrantedAuthority> authorities = decodedJWT.getClaim(ROLES).asList(SimpleGrantedAuthority.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}