package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;
import static java.time.LocalDateTime.now;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String purpose;
    private String description;
    @OneToOne
    private User user;
    @Setter(NONE)
    private LocalDateTime timeStamp;
    @PrePersist
    private void setTimeStamp(){
        timeStamp= now();
    }
}