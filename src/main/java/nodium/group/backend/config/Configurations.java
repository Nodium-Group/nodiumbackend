package nodium.group.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import nodium.group.backend.data.models.OTP;
import nodium.group.backend.data.models.Tokens;
import nodium.group.backend.data.repository.OTPRepository;
import nodium.group.backend.data.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static nodium.group.backend.utils.AppUtils.LOGIN_URL;

@Configuration
@RequiredArgsConstructor
public class Configurations {
    private final TokenRepository tokenRepository;
    private final OTPRepository otpRepository;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllTokens(){
        List<Tokens> allInvalidTokes = tokenRepository.findAll().stream().filter(Tokens::isBlackListed).toList();
        tokenRepository.deleteAll(allInvalidTokes);
    }
    @Scheduled(cron = "0 0 0/2 * * *")
    public void deleteInvalidOtps(){
        List<OTP> uselessOtps = otpRepository.findAll().stream().filter(otp ->
                Duration.between(otp.getTimeGenerated(),
                LocalDateTime.now()).toSeconds()>120).toList();
        otpRepository.deleteAll(uselessOtps);
    }

}
