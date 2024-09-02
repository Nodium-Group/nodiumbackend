package nodium.group.backend.utils;

import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.exception.BackEndException;

import java.util.List;
import java.util.Optional;

import static nodium.group.backend.exception.ExceptionMessages.INVALID_DETAILS_PROVIDED;

public class AppUtils {
    public static String APP_NAME = "NODIUM";
    public static String ROLES = "Roles";
    public static String AUTH_HEADER_PREFIX = "Bearer ";
    public static String LOGIN_URL = "/api/v1/nodium/login";
    public static String REGISTER_URL = "/api/v1/nodium/Users/Register";
    public static String SECRET = "Our Application name is Nodium Backend";
    public static List<String> PUBLIC_URLS = List.of(REGISTER_URL,LOGIN_URL);
    public static String[] USER_END_POINTS = {"/api/v1/nodium/Users/post-jobs"};
    public static String[] PUBLIC_END_POINTS = {REGISTER_URL,LOGIN_URL,
    "/api/v1/providers/register"};
    public static void validateRegisterRequest(RegisterRequest request){
        validate(request.getEmail());
        validate(request.getPassword());
        validate(request.getFirstname());
        validate(request.getLastname());
    }
    public static void validate(String registerRequest) {
        if(Optional.ofNullable(registerRequest).isEmpty())
            throw new BackEndException(INVALID_DETAILS_PROVIDED.getMessage());
    }
}
