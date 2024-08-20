package nodium.group.backend.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    EMAIL_ALREADY_EXIST("Email provided already Exist"),
    INVALID_DETAILS("Invalid Details Provided");
    ExceptionMessages(String message){
        this.message = message;
    }
    final String message;
}
