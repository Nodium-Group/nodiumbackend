package nodium.group.backend.handler;

import nodium.group.backend.exception.BackEndException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;

import static nodium.group.backend.exception.ExceptionMessages.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class BackendHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<?> BadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("Error",BAD_CREDENTIALS.getMessage(),"success",false));
    }
    @ExceptionHandler(BackEndException.class)
    @ResponseBody
    public ResponseEntity<?> backEndException(BackEndException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("Error",exception.getMessage(),"success",false));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> dataIntegrityViolation(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("Error",INVALID_DETAILS.getMessage(),"success",false));
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
        public ResponseEntity<?> exceptionHandler(Exception exception){
        return ResponseEntity.status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("Error",exception.getMessage(),"success",false));
    }
}
