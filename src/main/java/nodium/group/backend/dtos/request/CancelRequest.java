package nodium.group.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
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
