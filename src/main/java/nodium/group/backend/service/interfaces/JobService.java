package nodium.group.backend.service.interfaces;

import nodium.group.backend.data.models.Service;
import nodium.group.backend.dtos.request.DeleteJobRequest;
import nodium.group.backend.dtos.out.JobResponse;

import java.util.List;

public interface JobService {
    Service addService(Service job);
    void deleteJob(DeleteJobRequest deleteJobRquest);
    List<JobResponse> findAllJobs(String email);
}