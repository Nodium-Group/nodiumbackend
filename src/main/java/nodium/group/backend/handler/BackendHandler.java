package nodium.group.backend.handler;

import nodium.group.backend.dto.out.ApiResponse;
import nodium.group.backend.exception.BackEndException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;
import static java.time.LocalDateTime.now;
@RestControllerAdvice
public class BackendHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<?> BadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(false,"errorMessage : "+exception.getMessage(), now()));
    }
    @ExceptionHandler(BackEndException.class)
    @ResponseBody
    public ResponseEntity<?> backEndException(BackEndException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false,"errorMessage : "+exception.getMessage(), now()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> dataIntegrityViolation(DataIntegrityViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false,"Error : " +INVALID_DETAILS.getMessage(), now()));
    }
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandler(Exception exception){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ApiResponse(false ,"Error : " +exception.getMessage(), now()));
//    }
}
