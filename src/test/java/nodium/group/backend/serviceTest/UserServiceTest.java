package nodium.group.backend.serviceTest;

import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.request.DeleteJobRequest;
import nodium.group.backend.request.JobRequest;
import nodium.group.backend.request.RegisterRequest;
import nodium.group.backend.service.interfaces.UserService;
import nodium.group.backend.response.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestPropertySource(properties = {
        "DATABASE_URL=jdbc:postgresql://localhost:5432/nodiumgroup",
        "DATABASE_USERNAME=postgres",
        "DATABASE_PASSWORD=password"
})

@Slf4j
public class UserServiceTest {
     @Autowired
     private UserService userService;
      @Test
      void testUserCanPostJob(){
          RegisterRequest request = new RegisterRequest("email@email.com","Password12,",
                  "firstname","lastname");
         var registerResponse =  userService.registerUser(request);
         assertNotNull(registerResponse);
          log.info("Registerresponse --->{}",registerResponse);
          JobRequest  jobRequest = new JobRequest(registerResponse.getId(),"mainland Lagos","work at home",
                  "basket picking", new BigDecimal("20000"),"REMOTE");
          var jobResponse = userService.postJob(jobRequest);
          assertNotNull(jobResponse);
          log.info("JobResponse --->{}",jobResponse);
      }
      @Test
    void testUserCanCreateJobCreated(){
          DeleteJobRequest deleteJobRquest = new DeleteJobRequest("email@email.com",1L);
          userService.deleteJob(deleteJobRquest);
          assertNull(userService.findAllJobsCreatedByUser("email@email.com"));
      }
        //todo: test user can post job👍
        // todo: test user can delete job posted
        //todo: test user can book another user service
        // test user can cancel booking
        // test user can accept booked service
        // test user can decline booking
        // test user can update job posted
        // test firms can post jobs
        // test user can apply for firm job opporptunities
}
