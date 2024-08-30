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
    private String lga;
    private String houseNumber;
    private String street;
}
