package nodium.group.backend.service.interfaces;

import jakarta.validation.Valid;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.models.User;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.dto.out.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface UserService {
    RegisterResponse registerUser(@Validated RegisterRequest request);
    BookServiceResponse bookService(@Validated BookServiceRequest request);
    BookServiceResponse cancelBooking(@Validated CancelRequest cancelRequest);
    void deleteJob(DeleteJobRequest deleteJobRquest);
    RegisterResponse updateAddress(UpdateAddressRequest updateRequest);
    ReviewResponse dropReview(ReviewRequest request);
    User getUserByEmail(String username);
    JobResponse postJob(JobRequest jobRequest);
    List<User> findAllByRole(Role role);
    List<JobResponse> findAllJobsCreatedByUser(String email);
    List<NotificationResponse> getUserNotifications(Long userId);
    RegisterResponse updatePassword(UpdatePasswordRequest updatePassword);
}
