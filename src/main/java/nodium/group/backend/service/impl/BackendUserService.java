package nodium.group.backend.service.impl;

import jakarta.validation.Valid;
import nodium.group.backend.data.enums.OrderStatus;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.models.*;
import nodium.group.backend.data.repository.NotificationRepository;
import nodium.group.backend.data.repository.OrderRepository;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.data.repository.ReviewRepository;
import nodium.group.backend.response.*;
import nodium.group.backend.service.interfaces.JobService;
import nodium.group.backend.service.interfaces.NotificationService;
import nodium.group.backend.service.interfaces.UserService;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.request.*;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static nodium.group.backend.data.enums.Role.USER;
import static nodium.group.backend.exception.ExceptionMessages.EMAIL_ALREADY_EXIST;
import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;

@Component
public class BackendUserService implements UserService {
    @Autowired
    public BackendUserService(ModelMapper mapper, PasswordEncoder encoder, JobService service,
                              UserRepository repository, ReviewRepository reviewRepo,
                              NotificationService notificationService, OrderRepository orderRepo,
                              NotificationRepository notificationRepo){
        modelMapper= mapper;
        passwordEncoder= encoder;
        jobService = service;
        reviewRepository = reviewRepo;
        userRepository = repository;
        orderRepository= orderRepo;
        notificationRepository= notificationRepo;
        this.notificationService= notificationService;
    }
    @Override
    public RegisterResponse registerUser(@Valid RegisterRequest registerRequest) {
        try {
            validateMail(registerRequest.getEmail());
            User user = modelMapper.map(registerRequest, User.class);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.getRole().add(USER);
            user = userRepository.save(user);
            return modelMapper.map(user, RegisterResponse.class);
        }
        catch(DataIntegrityViolationException | ConstraintViolationException error){
            throw new BackEndException(EMAIL_ALREADY_EXIST.getMessage());
        }
    }
    @Override
    public RegisterResponse updateAddress(UpdateAddressRequest updateRequest) {
        User user = userRepository.findByEmailIgnoreCase(updateRequest.getEmail()).get();
        user.setAddress(modelMapper.map(updateRequest, Address.class));
        var addressAdded = modelMapper.map(user.getAddress(), AddressResponse.class);
        return new RegisterResponse(user.getId(),user.getFirstname(),user.getEmail(),addressAdded);
    }
    @Override
    public User getUserByEmail(String username) {
        return userRepository.findByEmailIgnoreCase(username).
                orElseThrow(()->new BackEndException(INVALID_DETAILS.getMessage()));
    }
    @Override
    public ReviewResponse dropReview(ReviewRequest request) {
        User reviewer= userRepository.findByEmailIgnoreCase(request.getUserEmail()).get();
        var reviewee = userRepository.findByEmailIgnoreCase(request.getProviderEmail()).get();
        Review review = new Review(null, LocalDateTime.now(),request.getReview(),reviewer,reviewee);
        review = reviewRepository.save(review);
        return new ReviewResponse(review.getId(),reviewer.getEmail(), review.getReviewContent());
    }
    @Override
    public JobResponse postJob(JobRequest jobRequest) {
        User user = userRepository.findById(jobRequest.getUserId()).get();
        Service service = modelMapper.map(jobRequest, Service.class);
        service.setPoster(user);
        service = jobService.addService(service);
        notificationService.notifyAllProvidersInLocation(service.getLocation(), jobRequest);
        return new JobResponse(service.getId(),service.getPoster().getId(), service.getDescription(),
                service.getCategory(),service.getLocation(), LocalDateTime.now());
    }
    @Override
    public BookServiceResponse cancelBooking(@Valid CancelRequest cancelRequest) {
        CustomerOrder order = getCustomerOrder(cancelRequest);
        notifyUserAndProvider(cancelRequest);
        return getBookResponse(cancelRequest, order);
    }

    @Override
    public void deleteJob(DeleteJobRequest deleteJobRquest) {
        jobService.deleteJob(deleteJobRquest);
    }

    @Override
    public List<JobResponse> findAllJobsCreatedByUser(String email) {
        return jobService.findAllJobs(email);
    }

    @Override
    public List<User> findAllByRole(Role role){
        return userRepository.findAll().stream().filter(user -> user.getRole().contains(role)).toList();
    }
    @Override
    public BookServiceResponse bookService(@Valid BookServiceRequest bookRequest) {
        CustomerOrder customerOrder = buildOrder(bookRequest);
        customerOrder = orderRepository.save(customerOrder);
        Notification notification = createNotification(bookRequest);
        notificationRepository.save(notification);
        return BookServiceResponse.builder().orderId(customerOrder.getId())
                .providerId(customerOrder.getProvider().getId()).customerId(bookRequest.getId())
                .status(customerOrder.getStatus()).timeStamp(customerOrder.getTimeStamp())
                .bookingMessage(bookRequest.getOrderDescription()).build();
    }
    private Notification notifyUser(Long userId, String description) {
        return Notification.builder()
                .purpose("Order was "+ OrderStatus.TERMINATED.name())
                .user(userRepository.findById(userId).get())
                .description(description)
                .build();
    }
    private CustomerOrder buildOrder(BookServiceRequest bookRequest){
        return CustomerOrder.builder()
                .status(OrderStatus.PENDING)
                .customer(userRepository.findById(bookRequest.getId()).get())
                .provider(userRepository.findById(bookRequest.getServiceId()).get())
                .build();
    }
    private void notifyUserAndProvider(CancelRequest cancelRequest) {
        Notification notification= notifyUser(cancelRequest.getUserId(), cancelRequest.getReason());
        notificationRepository.save(notification);
        notification = notifyUser(userRepository.
                        findById(cancelRequest.getProviderId()).get().getId(),
                cancelRequest.getReason());
        notificationRepository.save(notification);
    }
    private void validateMail(String email){
        if(userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new DataIntegrityViolationException(EMAIL_ALREADY_EXIST.getMessage());
    }
    private CustomerOrder getCustomerOrder(CancelRequest cancelRequest) {
        CustomerOrder order = orderRepository.findById(cancelRequest.getOrderId()).get();
        order.setStatus(OrderStatus.TERMINATED);
        order.setTimeUpdated(now());
        orderRepository.save(order);
        return order;
    }
    private static BookServiceResponse getBookResponse(CancelRequest cancelRequest, CustomerOrder order) {
        return BookServiceResponse.builder().customerId(cancelRequest.getUserId())
                .timeUpdated(order.getTimeUpdated()).providerId(cancelRequest.getProviderId())
                .orderId(order.getId()).status(order.getStatus()).build();
    }
    private Notification createNotification(BookServiceRequest bookRequest){
        return Notification.builder()
                .user(userRepository.findById(bookRequest.getServiceId()).get())
                .description(bookRequest.getOrderDescription())
                .purpose("Service Booking")
                .build();
    }
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final NotificationRepository notificationRepository;
    private final JobService jobService;
    private final NotificationService notificationService;

}
