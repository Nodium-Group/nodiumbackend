package nodium.group.backend.service.interfaces;

import nodium.group.backend.data.models.Job;
import nodium.group.backend.data.models.Service;
import nodium.group.backend.request.DeleteJobRequest;
import nodium.group.backend.response.JobResponse;

import java.util.List;

public interface JobService {
    Service addService(Service job);
    void deleteJob(DeleteJobRequest deleteJobRequest);
    List<JobResponse> findAllJobs(String email);
}