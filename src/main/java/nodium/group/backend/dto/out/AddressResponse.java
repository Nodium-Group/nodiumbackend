package nodium.group.backend.dto.out;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressResponse {
    private Long id;
    private String state;
    private String nationality;
    private String phoneNumber;
    private String street;
}
