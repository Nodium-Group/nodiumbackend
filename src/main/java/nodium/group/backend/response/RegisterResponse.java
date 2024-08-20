package nodium.group.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {
    private Long id;
    private String firstname;
    private String email;
    private AddressResponse address;
}
