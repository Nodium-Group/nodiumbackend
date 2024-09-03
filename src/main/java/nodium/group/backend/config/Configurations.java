package nodium.group.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static nodium.group.backend.utils.AppUtils.LOGIN_URL;

@Configuration
@AllArgsConstructor
public class Configurations {
    @Autowired
    private TokenRepository tokenRepository;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    };
    @Bean
    public PasswordEncoder passwowrdEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void deleteAllTokens(){
        List<Tokens> allInvalidTokes = tokenRepository.findAll().stream().filter(Tokens::isBlackListed).toList();
        tokenRepository.deleteAll(allInvalidTokes);
    }

}
