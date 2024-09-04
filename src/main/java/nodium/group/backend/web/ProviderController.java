package nodium.group.backend.web;

import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.service.interfaces.ProviderService;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/providers/")
public class ProviderController {
    @Autowired
    private ProviderService userService;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequest request){
        return ResponseEntity.status(201)
                .body(userService.register(request));
    }
//    @PostMapping("getAllBooking")
//    public ResponseEntity<?> getAllBookings(){
//        return ResponseEntity.of()
//    }
}
