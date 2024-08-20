package nodium.group.backend.service.interfaces;

import nodium.group.backend.data.models.Job;
import nodium.group.backend.data.models.Service;

public interface JobService {
    Service addService(Service job);
}