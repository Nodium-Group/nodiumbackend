package nodium.group.backend.serviceTest;

import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.dtos.request.BookServiceRequest;
import nodium.group.backend.dtos.request.CancelRequest;
import nodium.group.backend.dtos.request.JobRequest;
import nodium.group.backend.dtos.request.RegisterRequest;
import nodium.group.backend.dtos.out.BookServiceResponse;
import nodium.group.backend.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
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
          log.info("RegisterResponse --->{}",registerResponse);
          JobRequest  jobRequest = new JobRequest(registerResponse.getId(),"mainland Lagos","work at home",
                  "basket picking", new BigDecimal("20000"),"REMOTE");
          var jobResponse = userService.postJob(jobRequest);
          assertNotNull(jobResponse);
          log.info("JobResponse --->{}",jobResponse);
      }
      @Test
      @Sql(scripts = {"/db/data.Users.sql"})
      void testUserCanBookAProviderJob(){
          BookServiceRequest bookServiceRequest = new BookServiceRequest(5L,"job",12L,
                  LocalDateTime.now(),new BigDecimal("200"));
          BookServiceResponse bookServiceResponse = userService.bookService(bookServiceRequest);
          assertNotNull(bookServiceResponse);
          log.info("BookService Response--> {}",bookServiceResponse);
      }
        @Test
        @Sql(scripts = {"/db/data.Users.sql"})
        void testUserAndProviderRecieveNotificactionWhenServiceIsBooked(){
            assertEquals(0,userService.getUserNotifications(5L).size());
            assertEquals(0,userService.getUserNotifications(12L).size());
            BookServiceRequest bookServiceRequest = new BookServiceRequest(5L,"job",12L, LocalDateTime.now(),new BigDecimal("200"));
            BookServiceResponse bookServiceResponse = userService.bookService(bookServiceRequest);
            assertNotNull(bookServiceResponse);
            log.info("BookService Response--> {}",bookServiceResponse);
            assertEquals(1,userService.getUserNotifications(5L).size());
            assertEquals(1,userService.getUserNotifications(12L).size());
          }
        @Test
        @Sql(scripts = {"/db/data.Users.sql"})
        void testUserCanCancelBookings(){
            BookServiceRequest bookServiceRequest = new BookServiceRequest(5L,"job",12L, LocalDateTime.now(),new BigDecimal("200"));
            BookServiceResponse response = userService.bookService(bookServiceRequest);
            assertEquals(1,userService.getUserNotifications(5L).size());
            assertEquals(1,userService.getUserNotifications(12L).size());
            CancelRequest cancelRequest = new CancelRequest(response.getCustomerId(),
                                             response.getOrderId(),"not interested");
            BookServiceResponse cancelResponse = userService.cancelBooking(cancelRequest);
            assertNotNull(cancelResponse);
            log.info("CANCELRESPONSE -----> {}",cancelResponse);
        }
        // test user can cancel booking
        // test user can accept booked service
        // test user can decline booking
        // test user can update job posted
        // test firms can post jobs
        // test user can apply for firm job opporptunities
}


//todo: test user can post job👍
// todo: test user can delete job posted👍
//todo: test user can book another user service

