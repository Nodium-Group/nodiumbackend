package nodium.group.backend.dtos.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequest {
    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Mail not valid for Registration")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9.,&+/\\-]{7,11}$",
            message = "Alphanumeric password and special characters is required")
    private String password;
    @Pattern(regexp = "^[a-zA-Z]{2,}")
    private String firstname;
    @Pattern(regexp = "^[a-zA-Z]{2,}")
    private String lastname;
}
