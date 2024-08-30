package nodium.group.backend.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    EMAIL_ALREADY_EXIST("Email provided already Exist"),
    SOMETHING_WENT_WRONG("Something went wrong"),
    INVALID_DETAILS("Invalid Details Provided or Details already Exist");
    ExceptionMessages(String message){
        this.message = message;
    }
    final String message;
}
