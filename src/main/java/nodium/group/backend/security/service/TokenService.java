package nodium.group.backend.security.service;

public interface TokenService {
    void blacklistToken(String token);
    boolean isValid(String token);
}
