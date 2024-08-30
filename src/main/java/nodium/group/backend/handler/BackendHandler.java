package nodium.group.backend.handler;

import nodium.group.backend.exception.BackEndException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS;
import static nodium.group.backend.exception.ExceptionMessages.SOMETHING_WENT_WRONG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class BackendHandler {
    @ExceptionHandler(BackEndException.class)
    @ResponseBody
    public ResponseEntity<?> handleEventException(BackEndException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",exception.getMessage(),"success",false));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> handleEventException(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",INVALID_DETAILS.getMessage(),"success",false));
    }
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> handleEventException(Exception exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("Error",SOMETHING_WENT_WRONG.getMessage(),"success",false));
    }
}
