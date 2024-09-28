package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.Tokens;
import org.antlr.v4.runtime.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Tokens,Long> {
    @Query("SELECT d FROM Tokens d where d.token=:data")
    Optional<Tokens> findByToken(String data);
    @Query("select t from Tokens t where t.user.email=:email")
    Optional<Tokens> findByUser_Email(String email);
}
