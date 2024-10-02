package nodium.group.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateAddressRequest {
    @NotBlank
    private String street;
    @NotBlank
    private String state;
    private String email;
    private String nationality;
    private String stateOfOrigin;
    private String phoneNumber;
}
