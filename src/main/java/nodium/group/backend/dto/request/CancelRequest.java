package nodium.group.backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelRequest {
    private Long userId;
    private Long orderId;
    private String reason;
}
