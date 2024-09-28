package nodium.group.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UpdatePasswordRequest {
    private String email;
    private String password;
}
