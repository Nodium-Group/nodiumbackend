package nodium.group.backend.web;

import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.service.impl.BackendProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api/v1/nodium/")
public class ProviderController {
    @Autowired
    private BackendProviderService userService;
    @PostMapping("providers/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true,userService.register(request), LocalDateTime.now()));
    }
    @PostMapping("providers/send_otp/{email}")
    public ResponseEntity<?> sendOTP(@PathVariable("email") String email){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,"OTP sent Successfully", LocalDateTime.now()));

    }
    @GetMapping("providers/getAllBooking/{id}")
    public ResponseEntity<?> getAllBookings(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,userService.getAllBookings(id), LocalDateTime.now()));
    }
    @PatchMapping("providers/cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelRequest cancelBooking){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.cancelBooking(cancelBooking), LocalDateTime.now()));
    }
    @PostMapping("providers/book-service")
    public ResponseEntity<?> response(@RequestBody BookServiceRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, userService.bookService(request), LocalDateTime.now()));
    }
    @GetMapping("providers/get-notifications/{id}")
    public ResponseEntity<?> getAllNotifications(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.getUserNotifications(id), LocalDateTime.now()));
    }
    @PatchMapping("providers/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, userService.updateAddress(request), LocalDateTime.now()));
    }
    @PostMapping("providers/change-password")
    public ResponseEntity<?> resetPassword(@RequestBody UpdatePasswordRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true,userService.updatePassword(request), LocalDateTime.now()));
    }
}
