package nodium.group.backend.service.impl;

import nodium.group.backend.data.models.User;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.dto.out.RegisterResponse;
import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.service.interfaces.ProviderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import static nodium.group.backend.data.enums.Role.PROVIDER;
import static nodium.group.backend.exception.ExceptionMessages.EMAIL_ALREADY_EXIST;

@Component
@Validated
public class BackendProviderService implements ProviderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mmodelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = mmodelMapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        validateMail(request.getEmail());
        user.setRole(PROVIDER);
        user= userRepository.save(user);
        return mmodelMapper.map(user,RegisterResponse.class);
    }
    private void validateMail(String email){
        if(userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new BackEndException(EMAIL_ALREADY_EXIST.getMessage());
    }
}
