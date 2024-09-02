package nodium.group.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nodium.group.backend.security.filter.AuthenticationFilter;
import nodium.group.backend.security.manager.BackendAuthManager;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static nodium.group.backend.utils.AppUtils.LOGIN_URL;

@Configuration
@AllArgsConstructor
public class Configurations {
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

}
