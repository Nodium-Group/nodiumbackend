package nodium.group.backend.serviceTest;

import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.dto.request.BookServiceRequest;
import nodium.group.backend.dto.request.CancelRequest;
import nodium.group.backend.dto.request.JobRequest;
import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.dto.out.BookServiceResponse;
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
      @Sql(scripts = {"/db/data.organizer.sql"})
      void testUserCanBookAProviderJob(){
          BookServiceRequest bookServiceRequest = new BookServiceRequest(1L,"job",2L,
                  LocalDateTime.now(),new BigDecimal("200"));
          BookServiceResponse bookServiceResponse = userService.bookService(bookServiceRequest);
          assertNotNull(bookServiceResponse);
          log.info("BookService Response--> {}",bookServiceResponse);
      }
        @Test
        @Sql(scripts = {"/db/data.organizer.sql"})
        void testUserAndProviderRecieveNotificactionWhenServiceIsBooked(){
            assertEquals(0,userService.getUserNotifications(1L).size());
            assertEquals(0,userService.getUserNotifications(2L).size());
            BookServiceRequest bookServiceRequest = new BookServiceRequest(1L,"job",
                    2L, LocalDateTime.now(),new BigDecimal("200"));
            BookServiceResponse bookServiceResponse = userService.bookService(bookServiceRequest);
            assertNotNull(bookServiceResponse);
            log.info("BookService Response--> {}",bookServiceResponse);
            assertEquals(1,userService.getUserNotifications(1L).size());
            assertEquals(1,userService.getUserNotifications(2L).size());
          }
        @Test
        @Sql(scripts = {"/db/data.organizer.sql"})
        void testUserCanCancelBookings(){
            BookServiceRequest bookServiceRequest = new BookServiceRequest(1L,"job",2L,
                    LocalDateTime.now(),new BigDecimal("200"));
            BookServiceResponse response = userService.bookService(bookServiceRequest);
            assertEquals(1,userService.getUserNotifications(1L).size());
            assertEquals(1,userService.getUserNotifications(2L).size());
            userService.getUserNotifications(1L).stream()
                    .forEach(notificationResponse -> {log.info("Notification----->{}",notificationResponse);});
            userService.getUserNotifications(2L).stream()
                    .forEach(notificationResponse -> {log.info("Notification----->{}",notificationResponse);});
            CancelRequest cancelRequest = new CancelRequest(response.getCustomerId(),response.getOrderId(), "Not interested again");
            BookServiceResponse cancelResponse = userService.cancelBooking(cancelRequest);
            assertNotNull(cancelResponse);
            log.info("CANCELRESPONSE -----> {}",cancelResponse);
            assertEquals(2,userService.getUserNotifications(1L).size());
            userService.getUserNotifications(1L).stream()
                    .forEach(notificationResponse -> {log.info("Notification----->{}",notificationResponse);});
            userService.getUserNotifications(2L).stream()
                    .forEach(notificationResponse -> {log.info("Notification----->{}",notificationResponse);});
            assertEquals(2,userService.getUserNotifications(2L).size());
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

