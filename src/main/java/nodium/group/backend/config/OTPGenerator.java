package nodium.group.backend.config;

import nodium.group.backend.data.models.OTP;
import nodium.group.backend.data.repository.OTPRepository;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class OTPGenerator{
    private OTPRepository otpRepository;
    private UserService userService;
    public String generatePin(String reciepient){
        String pin =String.valueOf(100000 + new SecureRandom().nextInt(899_999));
        OTP otp= new OTP(null,pin,userService.getUserByEmail(reciepient), LocalDateTime.now());
        return pin;
    }
    public boolean confirmPin(String pin,String email){
        return otpRepository.findByPinAndUserEmail(pin,email).isPresent();
    }
}
