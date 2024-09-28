package nodium.group.backend.web;


import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api/v1/nodium/")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("users/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(true, userService.registerUser(request), LocalDateTime.now()));
    }
    @PostMapping("users/post-jobs")
    public ResponseEntity<?> postJobs(@RequestBody JobRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.postJob(request), LocalDateTime.now()));
    }
    @PatchMapping("users/cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelRequest cancelRequest){
        return ResponseEntity.status(200).body(new ApiResponse(true,
                userService.cancelBooking(cancelRequest), LocalDateTime.now()));
    }
    @PatchMapping("users/book-a-service")
    public ResponseEntity<?> cancelBooking(@RequestBody BookServiceRequest bookServiceRequest){
        return ResponseEntity.status(200).body(new ApiResponse(true,
                userService.bookService(bookServiceRequest), LocalDateTime.now()));
    }
    @PatchMapping("update-password")
    public ResponseEntity<?> updatePassword(UpdatePasswordRequest updatePassword){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.updatePassword(updatePassword), LocalDateTime.now()));
    }
    @PostMapping("users/drop-review")
    public ResponseEntity<?> review(ReviewRequest request){
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApiResponse(true,userService.dropReview(request), LocalDateTime.now()));
    }
    @PatchMapping("users/update-address")
    public ResponseEntity<?> updateAddress(UpdateAddressRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.updateAddress(request), LocalDateTime.now()));
    }
    @PostMapping("users/get-OTP/{email}")
    public ResponseEntity<?> getOTP(@PathVariable("email") String email){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,"OTP sent Successfully", LocalDateTime.now()));
    }

}
