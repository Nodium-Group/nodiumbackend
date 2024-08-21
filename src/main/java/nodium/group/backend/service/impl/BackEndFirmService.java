package nodium.group.backend.service.impl;

import nodium.group.backend.request.FirmJobRequest;
import nodium.group.backend.request.FirmRegisterRequest;
import nodium.group.backend.response.FirmJobResponse;
import nodium.group.backend.response.FirmRegisterResponse;
import nodium.group.backend.service.interfaces.FirmService;
import org.springframework.stereotype.Component;

@Component
public class BackEndFirmService implements FirmService {

    @Override
    public FirmRegisterResponse register(FirmRegisterRequest register) {
        return null;
    }

    @Override
    public FirmJobResponse postJob(FirmJobRequest jobRequest) {
        return null;
    }
}
