package nodium.group.backend.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.List;

import static nodium.group.backend.utils.AppUtils.*;

public class Utils {
    public static String generateToken(List<String> stringList){
        String token = JWT.create()
                .withExpiresAt(Instant.now().plusSeconds(60*60*72))
                .withClaim(ROLES, stringList)
                .withIssuer(APP_NAME)
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        return null;
    }
}
