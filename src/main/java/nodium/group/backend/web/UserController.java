package nodium.group.backend.web;

import jakarta.validation.Valid;
import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.request.*;
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
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.status(CREATED).body(
                new ApiResponse(true, userService.registerUser(request), now()));
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
    @PatchMapping("book-a-service")
    public ResponseEntity<?> cancelBooking(@RequestBody BookServiceRequest bookServiceRequest){
        return ResponseEntity.status(200).body(new ApiResponse(true,
                userService.bookService(bookServiceRequest),now()));
    }
    @PatchMapping("update-password")
    public ResponseEntity<?> updatePassword(UpdatePasswordRequest updatePassword){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.updatePassword(updatePassword),now()));
    }
    @PostMapping("drop-review")
    public ResponseEntity<?> review(ReviewRequest request){
        return ResponseEntity.status(OK).
                body(new ApiResponse(true,userService.dropReview(request),now()));
    }
    @PatchMapping("update-address")
    public ResponseEntity<?> updateAddress(UpdateAddressRequest request){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.updateAddress(request),now()));
    }
}
