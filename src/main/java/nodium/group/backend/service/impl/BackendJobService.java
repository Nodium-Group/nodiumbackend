package nodium.group.backend.service.impl;

import nodium.group.backend.data.models.Job;
import nodium.group.backend.data.models.Service;
import nodium.group.backend.data.repository.JobRepository;
import nodium.group.backend.request.DeleteJobRequest;
import nodium.group.backend.response.JobResponse;
import nodium.group.backend.service.interfaces.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BackendJobService implements JobService {
    @Autowired
    private JobRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Service addService(Service service) {
        return repository.save(service);
    }

    @Override
    public void deleteJob(DeleteJobRequest deleteJobRquest) {
       var job =  repository.findById(deleteJobRquest.getId());
       job.get().setDeleted(true);
       repository.save(job.get());
    }

    @Override
    public List<JobResponse> findAllJobs(String email) {
        return repository.findAll().stream().filter(job-> !job.isDeleted())
                .filter(job->job.getPoster().getEmail().equalsIgnoreCase(email))
                .map(job->modelMapper.map(job,JobResponse.class))
                .toList();
    }
}
