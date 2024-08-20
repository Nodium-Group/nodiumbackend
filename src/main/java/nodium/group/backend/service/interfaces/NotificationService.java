package nodium.group.backend.service.interfaces;

import nodium.group.backend.request.JobRequest;

public interface NotificationService {
    void notifyAllProvidersInLocation(String location, JobRequest jobRequest);
}
