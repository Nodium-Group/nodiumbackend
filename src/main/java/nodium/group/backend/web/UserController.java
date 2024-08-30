package nodium.group.backend.web;

import nodium.group.backend.dtos.out.ApiResponse;
import nodium.group.backend.dtos.request.RegisterRequest;
import nodium.group.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/Users/")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("Register/user")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(200).body(
                new ApiResponse(true,userService.registerUser(request), LocalDateTime.now()));
    }
}
