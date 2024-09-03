package nodium.group.backend.security.service;

import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.repository.TokenRepository;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

import static nodium.group.backend.security.filter.AuthorizationFilter.buildVerifier;

@Component
public class BackendTokenService implements TokenService{
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;
    @Override
    public void blacklistToken(String token) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var user = userService.getUserByEmail(username);
        Tokens tokenObject = new Tokens(null,token,user,true);
        tokenRepository.save(tokenObject);
    }
    @Override
    public boolean isValid(String token) {
        var isAvailable =  tokenRepository.findByToken(token).isPresent();
        if(isAvailable) buildVerifier().verify(token);
        return true;
    }


}
