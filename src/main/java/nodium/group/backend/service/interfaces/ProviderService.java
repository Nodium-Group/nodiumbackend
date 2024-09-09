package nodium.group.backend.service.interfaces;

import nodium.group.backend.dto.out.RegisterResponse;
import nodium.group.backend.dto.request.RegisterRequest;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface ProviderService {
    RegisterResponse register(@Validated RegisterRequest request);

    List<?> getAllBookings(Long id);
}
