package nodium.group.backend.web;

import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.dto.request.*;
import nodium.group.backend.service.impl.BackendProviderService;
import nodium.group.backend.service.impl.BackendUserService;
import nodium.group.backend.service.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/providers/")
public class ProviderController {
    @Autowired
    private BackendProviderService userService;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequest request){
        return ResponseEntity.status(201)
                .body(userService.register(request));
    }
    @GetMapping("getAllBooking/{id}")
    public ResponseEntity<?> getAllBookings(@PathVariable("id") Long id){
        return ResponseEntity.status(OK).body(userService.getAllBookings(id));
    }
    @PatchMapping("cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestBody CancelRequest cancelBooking){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.cancelBooking(cancelBooking),now()));
    }
    @PostMapping("book-service")
    public ResponseEntity<?> response(@RequestBody BookServiceRequest request){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true, userService.bookService(request),now()));
    }
    @GetMapping("get-notifications/{id}")
    public ResponseEntity<?> getAllNotifications(@PathVariable("id") Long id){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.getUserNotifications(id),now()));
    }
    @PatchMapping("update-address")
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequest request){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true, userService.updateAddress(request),now()));
    }
    @PostMapping("change-password")
    public ResponseEntity<?> res(@RequestBody UpdatePasswordRequest request){
        return ResponseEntity.status(OK)
                .body(new ApiResponse(true,userService.updatePassword(request),now()));
    }
}
