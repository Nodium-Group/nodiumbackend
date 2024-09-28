package nodium.group.backend.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.repository.TokenRepository;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.exception.BackEndException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static nodium.group.backend.utils.AppUtils.AUTH_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequestMapping("/api/v1/auth/")
@Controller
public class Auths {
    @Autowired
    private TokenRepository tokenRepository;
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        try {
            LoginRequest loginRequest= new ModelMapper().map(request.getInputStream(),LoginRequest.class);
            Optional<Tokens> token = tokenRepository.findByUser_Email(loginRequest.getEmail());
            tokenRepository.delete(token.get());
            return ResponseEntity.noContent().build();
        }catch(Exception error){
            return ResponseEntity.badRequest().build();
        }
    }
}
