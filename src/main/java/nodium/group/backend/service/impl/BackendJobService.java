package nodium.group.backend.service.impl;

import nodium.group.backend.data.models.Service;
import nodium.group.backend.data.repository.JobRepository;
import nodium.group.backend.data.repository.ServiceRepository;
import nodium.group.backend.dto.request.DeleteJobRequest;
import nodium.group.backend.dto.out.JobResponse;
import nodium.group.backend.service.interfaces.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackendJobService implements JobService {
    @Autowired
    private ServiceRepository repository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Service addService(Service service) {
        return repository.save(service);
    }

    @Override
    public void deleteJob(DeleteJobRequest deleteJobRequest) {
       var job =  jobRepository.findById(deleteJobRequest.getId()).get();
       jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> findAllJobs(String email) {
        return repository.findAll().stream().filter(job-> !job.isDeleted())
                .filter(job->job.getPoster().getEmail().equalsIgnoreCase(email))
                .map(job->modelMapper.map(job,JobResponse.class))
                .toList();
    }
}
