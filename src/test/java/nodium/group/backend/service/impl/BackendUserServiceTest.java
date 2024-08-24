package nodium.group.backend.service.impl;

import nodium.group.backend.data.enums.Role;
import nodium.group.backend.data.repository.JobRepository;
import nodium.group.backend.data.repository.ServiceRepository;
import nodium.group.backend.data.repository.UserRepository;
import nodium.group.backend.request.*;
import nodium.group.backend.response.JobResponse;
import nodium.group.backend.service.interfaces.JobService;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BackendUserServiceTest {

    @Autowired
    private BackendUserService backendUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUp(){
       // userRepository.deleteAll();
       // serviceRepository.deleteAll();
      //  jobRepository.deleteAll();
    }
    @Test
    void registerUser() {

        RegisterRequest registerRequest= RegisterRequest.builder()
                .email("test@email")
                .firstname("firstname")
                .lastname("lastname")
                .password("password")
                .build();
       var response = backendUserService.registerUser(registerRequest);
       assertThat(response.getEmail()).isEqualTo("test@email");
    }

    @Test
    void updateAddress() {

        RegisterRequest registerRequest= RegisterRequest.builder()
                .email("test@email")
                .firstname("firstname")
                .lastname("lastname")
                .password("password")
                .build();
        var response = backendUserService.registerUser(registerRequest);
        UpdateAddressRequest updateAddressRequest = UpdateAddressRequest.builder()
                .email("test@email")
                .lga("LGA")
                .state("state")
                .street("street")
                .houseNumber("12")
                .build();
        var Response = backendUserService.updateAddress(updateAddressRequest);
        assertThat(Response.getAddress().getLga()).isEqualTo("LGA");

    }

    @Test
    void getUserByEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("isaiahugoeze@gmail.com");
        registerRequest.setFirstname("Marvellous");
        registerRequest.setLastname("Isaiah");
        registerRequest.setPassword("12345");
        var response = backendUserService.registerUser(registerRequest);
        var Response = backendUserService.getUserByEmail(registerRequest.getEmail());
        assertThat(Response.getId()).isEqualTo(response.getId());

    }

    @Test
    @Sql(scripts = {"/db/data.Users.sql"})
    void dropReview() {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReview("");
        reviewRequest.setUserEmail("fatoye@gmail.com");
        reviewRequest.setProviderEmail("fatoye@gmail.com");
        var response = backendUserService.dropReview(reviewRequest);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getRevieweeEmail()).isEqualTo("fatoye@gmail.com");


    }

    @Test
    @Sql(scripts = {"/db/data.Users.sql"})

    void postJob() {
        JobRequest jobResponse = new JobRequest();
        jobResponse.setUserId(5L);
        jobResponse.setCategory("company");
        jobResponse.setDescription("tech");
        jobResponse.setLocation("Lagos");
        var response = backendUserService.postJob(jobResponse);
        assertThat(response.getUserId()).isEqualTo(5L);
    }

    @Test
    @Sql(scripts = {"/db/data.Users.sql"})
    void cancelBooking() {
        CancelRequest cancelRequest = new CancelRequest();
        cancelRequest.setUserId(5L);
        cancelRequest.setReason("insufficient funds");
        cancelRequest.setOrderId(2L);
        cancelRequest.setProviderId(5L);
        assertThat(cancelRequest.getReason()).isEqualTo("insufficient funds");




    }

    @Test
    @Sql(scripts = {"/db/data.Users.sql"})
    void deleteJob() {
        DeleteJobRequest deleteJobRequest = new DeleteJobRequest();
        deleteJobRequest.setEmail("fatoyeayomide@gmail.com");
        deleteJobRequest.setId(1L);
       backendUserService.deleteJob(deleteJobRequest);
        assertThat(deleteJobRequest.getEmail()).isEqualTo("fatoyeayomide@gmail.com");
    }

    @Test
    @Sql(scripts = {"/db/data.Users.sql"})
    void findAllJobsCreatedByUser() {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("isaiahugoeze@gmail.com");
//        registerRequest.setFirstname("Marvellous");
//        registerRequest.setLastname("Isaiah");
//        registerRequest.setPassword("12345");
//        var response = backendUserService.registerUser(registerRequest);
        JobRequest jobRequest = new JobRequest();
        jobRequest.setCategory("tech");
        jobRequest.setUserId(5L);
        jobRequest.setLocation("Lagos");
        jobRequest.setAmount(TEN);
        jobRequest.setName("job");
        backendUserService.postJob(jobRequest);
      var response2 =backendUserService.findAllJobsCreatedByUser("fatoye@gmail.com");
       assertThat(response2.size()).isEqualTo(1);
    }

    @Test
    void findAllByRole() {

       RegisterRequest registerRequest = RegisterRequest.builder()
               .email("test1@email")
               .firstname("firstname")
               .password("password")
               .build();
       backendUserService.registerUser(registerRequest);
     var response =  backendUserService.findAllByRole(Role.USER);
     assertThat(response.size()).isEqualTo(3);
    }

    @Test
    void bookService() {

    }

}