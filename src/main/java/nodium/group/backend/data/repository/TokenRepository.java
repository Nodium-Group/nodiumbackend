package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Tokens,Long> {
    Optional<Object> findByToken(String token);
}
