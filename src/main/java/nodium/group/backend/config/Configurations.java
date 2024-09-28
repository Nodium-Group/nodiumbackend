package nodium.group.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nodium.group.backend.data.models.OTP;
import nodium.group.backend.data.repository.OTPRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class Configurations {
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
    @Scheduled(cron = "0 30 0 */2 * *")
    public void deleteInvalidOtps(){
        List<OTP> uselessOtps = otpRepository.findAll().stream().filter(otp ->
                Duration.between(otp.getTimeGenerated(),
                LocalDateTime.now()).toSeconds()>120).toList();
        otpRepository.deleteAll(uselessOtps);
    }

}
