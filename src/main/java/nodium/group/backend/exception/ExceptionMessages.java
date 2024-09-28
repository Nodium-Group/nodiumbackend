package nodium.group.backend.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    EMAIL_ALREADY_EXIST("Email provided already Exist"),
    BAD_CREDENTIALS("Bad Credentials"),
    SOMETHING_WENT_WRONG("Something went wrong"),
    RE_LOGIN("Please try logging in again."),
    INVALID_DETAILS_PROVIDED("Invalid details Provided"),
    INVALID_DETAILS("Invalid Details Provided or Details already Exist");
    ExceptionMessages(String message){
        this.message = message;
    }
    final String message;
}
