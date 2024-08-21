package nodium.group.backend.service.interfaces;

import nodium.group.backend.request.FirmJobRequest;
import nodium.group.backend.request.FirmRegisterRequest;
import nodium.group.backend.response.FirmJobResponse;
import nodium.group.backend.response.FirmRegisterResponse;

public interface FirmService {
    FirmRegisterResponse register(FirmRegisterRequest register);

    FirmJobResponse postJob(FirmJobRequest jobRequest);
}