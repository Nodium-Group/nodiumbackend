package nodium.group.backend.utils;

import java.util.List;

public class AppUtils {
    public static String APP_NAME = "NODIUM";
    public static String ROLES = "Roles";
    public static String BEARER = "Bearer ";
    public static String LOGIN_URL = "/api/v1/nodium/login";
    public static String SECRET = "Our Application name is Nodium Backend";
    public static List<String> PUBLIC_URLS = List.of("/api/v1/nodium/Users/Register","/api/v1/nodium/login");
    public static String[] USER_END_POINTS = {"/api/v1/nodium/Users/post-jobs"};
    public static String[] PUBLIC_END_POINTS = {"/api/v1/nodium/Users/Register","/api/v1/nodium/login"};

}
