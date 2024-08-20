package nodium.group.backend.service.interfaces;

import jakarta.validation.Valid;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.models.User;
import nodium.group.backend.request.*;
import nodium.group.backend.response.BookServiceResponse;
import nodium.group.backend.response.JobResponse;
import nodium.group.backend.response.RegisterResponse;
import nodium.group.backend.response.ReviewResponse;

import java.util.List;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);
    BookServiceResponse bookService(BookServiceRequest request);
    BookServiceResponse cancelBooking(@Valid CancelRequest cancelRequest);
    void deleteJob(DeleteJobRequest deleteJobRquest);
    RegisterResponse updateAddress(UpdateAddressRequest updateRequest);
    ReviewResponse dropReview(ReviewRequest request);
    User getUserByEmail(String username);
    JobResponse postJob(JobRequest jobRequest);
    List<User> findAllByRole(Role role);
    List<JobResponse> findAllJobsCreatedByUser(String email);
}
