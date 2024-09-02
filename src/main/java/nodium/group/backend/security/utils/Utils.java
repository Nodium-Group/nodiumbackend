package nodium.group.backend.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.List;

import static nodium.group.backend.utils.AppUtils.*;

public class Utils {
    public static String generateToken(List<String> stringList){
        return JWT.create()
                .withExpiresAt(Instant.now().plusSeconds(60*60*24*3))
                .withClaim(ROLES, stringList)
                .withIssuer(APP_NAME)
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }
    public static JWTVerifier buildVerifier() {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .withClaimPresence(ROLES)
                .withIssuer(APP_NAME)
                .build();
    }
//    public static void  extractAndSetToken(String authorization, HttpServletRequest request, Integer number) throws Exception{
//        JWTVerifier verifier = buildVerifier();
//        String token = authorization.substring(AUTH_HEADER_PREFIX.length()).strip();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        List< ? extends GrantedAuthority> authorities = decodedJWT.getClaim(ROLES).asList(SimpleGrantedAuthority.class);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
}
