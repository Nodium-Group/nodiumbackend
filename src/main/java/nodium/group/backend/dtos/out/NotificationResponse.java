package nodium.group.backend.dtos.out;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NotificationResponse {
    private String description;
    private Long id;
}
