package nodium.group.backend.web;

import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.request.BookServiceRequest;
import nodium.group.backend.dto.request.CancelRequest;
import nodium.group.backend.dto.request.JobRequest;
import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/nodium/Users/")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("Register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(CREATED).body(
                new ApiResponse(true,userService.registerUser(request), now()));
    }
    @PostMapping("post-jobs")
    public ResponseEntity<?> postJobs(@RequestBody JobRequest request) {
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.postJob(request),now()));
    }
    @PatchMapping("cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelRequest cancelRequest){
        return ResponseEntity.status(200).body(new ApiResponse(true,
                userService.cancelBooking(cancelRequest),now()));
    }
    @PatchMapping("book")
    public ResponseEntity<?> cancelBooking(@RequestBody BookServiceRequest bookServiceRequest){
        return ResponseEntity.status(200).body(new ApiResponse(true,
                userService.bookService(bookServiceRequest),now()));
    }

}
