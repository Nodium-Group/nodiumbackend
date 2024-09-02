package nodium.group.backend.service.impl;

import jakarta.validation.Valid;
import nodium.group.backend.data.enums.OrderStatus;
import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.models.*;
import nodium.group.backend.data.repository.NotificationRepository;
import nodium.group.backend.data.repository.OrderRepository;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.dto.out.*;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.data.repository.ReviewRepository;
import nodium.group.backend.service.interfaces.JobService;
import nodium.group.backend.service.interfaces.NotificationService;
import nodium.group.backend.service.interfaces.UserService;
import nodium.group.backend.data.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static nodium.group.backend.data.enums.Role.USER;
import static nodium.group.backend.exception.ExceptionMessages.*;
import static nodium.group.backend.utils.AppUtils.validateRegisterRequest;

@Component
@Validated
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
    public RegisterResponse registerUser( RegisterRequest registerRequest) {
        try {
//            validateRegisterRequest(registerRequest);
            registerRequest.setEmail(registerRequest.getEmail().toLowerCase());
            validateMail(registerRequest.getEmail());
            User user = modelMapper.map(registerRequest, User.class);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole(USER);
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
        user=userRepository.save(user);
        var addressAdded = modelMapper.map(user.getAddress(), AddressResponse.class);
        return new RegisterResponse(user.getId(),user.getFirstname(),user.getLastname(),user.getEmail(),addressAdded);
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
        CustomerOrder order = orderRepository.findById(cancelRequest.getOrderId()).get();
        order.setTimeUpdated(now());
        order.setStatus(OrderStatus.TERMINATED);
        order =orderRepository.save(order);
        notifyUserAndProvider(order,cancelRequest.getReason());
        return new BookServiceResponse(cancelRequest.getReason(),order.getId(),
                order.getStatus(),order.getTimeStamp(), order.getProvider().getId(),
                order.getCustomer().getId(),order.getTimeUpdated());
    }
    private void notifyUserAndProvider(CustomerOrder order,String reason){
        notifyUser(order.getCustomer().getId(),reason,"Canceled Booking");
        notifyUser(order.getProvider().getId(),"Canceled Booking","Booking  was TERMINATED");
    }

    @Override
    public void deleteJob(DeleteJobRequest deleteJobRequest) {
        jobService.deleteJob(deleteJobRequest);
    }

    @Override
    public List<JobResponse> findAllJobsCreatedByUser(String email) {
        return jobService.findAllJobs(email);
    }

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUser(userRepository.findById(userId).get()).stream()
                .map(notification -> modelMapper.map(notification,NotificationResponse.class))
                .toList();
    }

    @Override
    public List<User> findAllByRole(Role role){
        return userRepository.findAll().stream().filter(user -> user.getRole().name().equals(role.name())).toList();
    }
    @Override
    public BookServiceResponse bookService(@Valid BookServiceRequest bookRequest) {
        CustomerOrder customerOrder = buildOrder(bookRequest);
        customerOrder = orderRepository.save(customerOrder);
        notifyUserAndProvider(bookRequest);
        return BookServiceResponse.builder().orderId(customerOrder.getId())
                .providerId(customerOrder.getProvider().getId()).customerId(bookRequest.getId())
                .status(customerOrder.getStatus()).timeStamp(customerOrder.getTimeStamp())
                .orderDescription(bookRequest.getOrderDescription()).build();
    }
    private Notification notifyProvider(Long userId, String description, OrderStatus status) {
        return Notification.builder()
                .purpose(String.format("You have a %s Order ",status))
                .user(userRepository.findById(userId).get())
                .description(description)
                .build();
    }
    private CustomerOrder buildOrder(BookServiceRequest bookRequest){
        return CustomerOrder.builder()
                .status(OrderStatus.PENDING)
                .customer(userRepository.findById(bookRequest.getId()).get())
                .provider(userRepository.findById(bookRequest.getUserId()).get())
                .build();
    }
//    private void notifyUserAndProvider(CancelRequest cancelRequest,OrderStatus status) {
//        Notification notification= notifyUser(cancelRequest.getUserId(), cancelRequest.getReason(),status);
//        notificationRepository.save(notification);
//        notification = notifyUser(userRepository.
//                        findById(cancelRequest.getProviderId()).get().getId(),
//                cancelRequest.getReason());
//        notificationRepository.save(notification);
//    }
    private void notifyUserAndProvider(BookServiceRequest request){
        Notification notification= notifyProvider(request.getUserId(),
                                   request.getOrderDescription(), OrderStatus.PENDING);
        notificationRepository.save(notification);
        notifyUser(request.getId(),request.getOrderDescription(),"You Booked a Service");

    }
    private void validateMail(String email){
        if(userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new DataIntegrityViolationException(EMAIL_ALREADY_EXIST.getMessage());
    }
    private void notifyUser(Long userId,String description,String purpose){
        var notification = new Notification(null,purpose,description,
                userRepository.findById(userId).get(),now(),false);
        notificationRepository.save(notification);
    }
//    private CustomerOrder getCustomerOrder(CancelRequest cancelRequest) {
//        CustomerOrder order = orderRepository.findById(cancelRequest.getOrderId()).get();
//        order.setStatus(OrderStatus.TERMINATED);
//        order.setTimeUpdated(now());
//        order=orderRepository.save(order);
//        return order;
//    }
//    private static BookServiceResponse getBookResponse(CancelRequest cancelRequest, CustomerOrder order) {
//        return BookServiceResponse.builder().customerId(cancelRequest.getUserId())
////                .timeUpdated(order.getTimeUpdated()).providerId(cancelRequest.getProviderId())
//                .orderId(order.getId()).status(order.getStatus()).build();
//    }
//    private Notification createNotification(BookServiceRequest bookRequest){
//        return Notification.builder()
//                .user(userRepository.findById(bookRequest.()).get())
//                .description(bookRequest.getOrderDescription())
//                .purpose("Service Booking")
//                .build();
//    }
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final NotificationRepository notificationRepository;
    private final JobService jobService;
    private final NotificationService notificationService;

}
