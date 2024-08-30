package nodium.group.backend.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private AddressResponse address;
}
