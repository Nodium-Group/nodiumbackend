package nodium.group.backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.models.User;
import nodium.group.backend.data.repository.ReviewRepository;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.dto.out.*;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.exception.ExceptionMessages;
import nodium.group.backend.service.interfaces.JobService;
import nodium.group.backend.service.interfaces.ProviderService;
import nodium.group.backend.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
public class BackendProviderService implements ProviderService {
    @Autowired
    public BackendProviderService(UserRepository userRepository, ModelMapper modelMapper,
                                  PasswordEncoder encoder, BackendJobService jobService,
                                  UserService userService, MailService mailService,
                                  ReviewRepository reviewRepository){
        this.userRepository = userRepository;
        this.modelMapper= modelMapper;
        this.passwordEncoder= encoder;
        this.jobService = jobService;
        this.userService = userService;
        this.mailService = mailService;
        this.reviewRepository= reviewRepository;
    }
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final JobService jobService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        validateMail(request.getEmail());
        User user = User.builder().firstname(request.getFirstname()).
                lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PROVIDER).build();
        user= userRepository.save(user);
        return modelMapper.map(user,RegisterResponse.class);
    }
    @Override
    public void sendOTP(String reciepient) throws MessagingException {
        mailService.sendOTP(reciepient);
    }

    @Override
    public List<?> getAllBookings(Long id) {
        var user = userRepository.findById(id).get();
        return jobService.findAllJobs(user.getEmail());
    }

    @Override
    public List<?> getAllReviews(Long id) {
        var user = userRepository.findById(id).get();
        return reviewRepository.findAllByReviewee(user);
    }

    private void validateMail(String email){
        if(userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new BackEndException(ExceptionMessages.EMAIL_ALREADY_EXIST.getMessage());
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        return userService.registerUser(request);
    }

    public BookServiceResponse bookService(BookServiceRequest request) {
        return userService.bookService(request);
    }

    public BookServiceResponse cancelBooking(CancelRequest cancelRequest) {
        return userService.cancelBooking(cancelRequest);
    }

    public void deleteJob(DeleteJobRequest deleteJobRquest) {
        userService.deleteJob(deleteJobRquest);
    }
    public RegisterResponse updateAddress(UpdateAddressRequest updateRequest) {
        return userService.updateAddress(updateRequest);
    }
    public ReviewResponse dropReview(ReviewRequest request) {
        return userService.dropReview(request);
    }
    public User getUserByEmail(String username) {
        return userService.getUserByEmail(username);
    }
    public JobResponse postJob(JobRequest jobRequest) {
        return userService.postJob(jobRequest);
    }
    public List<User> findAllByRole(Role role) {
        return userService.findAllByRole(role);
    }
    public List<JobResponse> findAllJobsCreatedByUser(String email) {
        return userService.findAllJobsCreatedByUser(email);
    }
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return userService.getUserNotifications(userId);
    }
    public RegisterResponse updatePassword(UpdatePasswordRequest updatePassword) {
        return userService.updatePassword(updatePassword);
    }
}
