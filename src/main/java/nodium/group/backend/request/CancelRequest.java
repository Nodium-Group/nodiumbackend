package nodium.group.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CancelRequest {
    private Long providerId;
    private Long userId;
    private Long orderId;
    @NotBlank
    private String reason;
}
