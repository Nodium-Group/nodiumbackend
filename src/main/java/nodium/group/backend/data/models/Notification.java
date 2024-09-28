package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String purpose;
    private String description;
    @ManyToOne
    private User user;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeStamp = now();
    private boolean isSeen;

}