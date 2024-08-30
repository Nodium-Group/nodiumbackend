package nodium.group.backend.dto.out;

import lombok.*;
import nodium.group.backend.data.models.User;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NotificationResponse {
    private String description;
    private Long id;
    private String purpose;
    @Lazy
    private User user;
    private LocalDateTime timeStamp;
    private boolean isSeen;
}
