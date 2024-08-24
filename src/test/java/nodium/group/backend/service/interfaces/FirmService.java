package nodium.group.backend.service.interfaces;

import nodium.group.backend.request.FirmRegisterRequest;
import nodium.group.backend.request.FirmRegisterResponse;

public interface FirmService {
    FirmRegisterResponse register(FirmRegisterRequest register);
}
