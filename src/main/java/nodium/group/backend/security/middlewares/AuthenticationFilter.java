package nodium.group.backend.security.middlewares;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.models.User;
import nodium.group.backend.data.repository.TokenRepository;
import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.out.ErrorResponse;
import nodium.group.backend.dto.out.LoginResponse;
import nodium.group.backend.dto.out.RegisterResponse;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.security.models.SecuredUser;
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
import java.time.Instant;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;
import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager manager;
    private final ObjectMapper mapper;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginRequest login;
        try{
            login = mapper.readValue(request.getInputStream(),LoginRequest.class);
            log.info("Email ={}", login.getEmail());
            log.info("password ={}",login.getPassword());
            Optional<Tokens> token = tokenRepository.findByUser_Email(login.getEmail());
            token.ifPresent(tokenRepository::delete);
        }
        catch(IOException exception){
            throw new BackEndException(exception.getMessage());
        }
        Authentication authentication= new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword());
        Authentication authResult = manager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("Authentication Done and Dusted");
        return authResult;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = getToken(authResult);
        User user = userService.getUserByEmail(authResult.getPrincipal().toString());
        Tokens tokenSaving = new Tokens(null,token,user,false);
        tokenRepository.save(tokenSaving);
        log.info("Persisting token -> {}",tokenSaving);
        log.info("Email := {}",authResult.getPrincipal());
        log.info("Password := {}",authResult.getCredentials());
        ApiResponse apiResponse = new ApiResponse(true,new LoginResponse(
                new ModelMapper().map(user, RegisterResponse.class),user.getRole(),token),now());
        response.getOutputStream().write(mapper.writeValueAsBytes(apiResponse));
        response.flushBuffer();
        log.info("sent token out");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        ErrorResponse error = new ErrorResponse(INVALID_DETAILS.getMessage(),request.getRequestURI());
        response.sendError(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(
                new ApiResponse(false,error,now())));
        response.flushBuffer();
        log.info("Unsuccessful authentication");
    }
    private String getToken(Authentication authentication){
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(APP_NAME)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(3,DAYS))
                .withSubject(authentication.getPrincipal().toString())
                .withClaim("principal",authentication.getPrincipal().toString())
                .withClaim("credentials", authentication.getCredentials().toString())
                .withArrayClaim("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
