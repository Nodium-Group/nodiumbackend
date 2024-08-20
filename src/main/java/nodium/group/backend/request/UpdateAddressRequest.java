package nodium.group.backend.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateAddressRequest {
    @Pattern(regexp = "//d+")
    private String houseNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String lga;
    @NotBlank
    private String state;
    private String email;
}
