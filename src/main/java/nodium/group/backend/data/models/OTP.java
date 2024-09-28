package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pin;
    @OneToOne
    private User user;
    private LocalDateTime timeGenerated;
}
