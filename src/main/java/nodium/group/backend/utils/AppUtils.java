package nodium.group.backend.utils;

import nodium.group.backend.dto.request.RegisterRequest;
import nodium.group.backend.exception.BackEndException;
import nodium.group.backend.exception.ExceptionMessages;

import java.util.Arrays;
import java.util.Optional;

public class AppUtils {
    public static String APP_NAME = "NODIUM";
    public static String ROLES = "Roles";
    public static Integer SEVEN = 7;
    public static String AUTH_HEADER_PREFIX = "Bearer ";
    public static String LOGIN_URL = "/api/v1/auth/login";
    public static String REGISTER_URL = "/api/v1/nodium/users/register";
    public static String SECRET = "Our Application name is Nodium Backend";
    public static String PROVIDER_END_POINT = "/api/v1/nodium/providers/register";
    public static String LOGOUT_URL = "/api/v1/auth/logout";
    public static String[] PUBLIC_END_POINTS = {
            REGISTER_URL,LOGIN_URL,PROVIDER_END_POINT,LOGOUT_URL};
    public static void validateRegisterRequest(RegisterRequest request){
        validate(request.getEmail());
        validate(request.getPassword());
        validate(request.getFirstname());
        validate(request.getLastname());
    }
    public static void validate(String registerRequest) {
        if(Optional.ofNullable(registerRequest).isEmpty())
            throw new BackEndException(ExceptionMessages.INVALID_DETAILS_PROVIDED.getMessage());
    }
    public static boolean checkMatches(String values){
        return Arrays.asList(PUBLIC_END_POINTS).contains(values);
    }
}
