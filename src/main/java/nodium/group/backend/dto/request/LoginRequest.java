package nodium.group.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoginRequest {
    @NotBlank(message = "Enail field is required")
    private String email;
    @NotBlank(message = "Enail field is required")
    private String password;
}
