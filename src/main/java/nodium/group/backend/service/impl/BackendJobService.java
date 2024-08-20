package nodium.group.backend.service.impl;

import nodium.group.backend.data.models.Job;
import nodium.group.backend.data.models.Service;
import nodium.group.backend.data.repository.JobRepository;
import nodium.group.backend.service.interfaces.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackendJobService implements JobService {
    @Autowired
    private JobRepository repository;
    @Override
    public Service addService(Service service) {
        return repository.save(service);
    }
}
