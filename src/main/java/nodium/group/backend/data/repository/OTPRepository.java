package nodium.group.backend.data.repository;

import nodium.group.backend.data.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query("SELECT o FROM OTP o WHERE o.pin = :pin AND o.user.email = :email")
    Optional<OTP> findByPinAndUserEmail(@Param("pin") String pin, @Param("email") String email);}
