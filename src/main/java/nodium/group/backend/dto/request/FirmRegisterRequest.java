package nodium.group.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FirmRegisterRequest {
    private String firmName;
    private String description;
    private UpdateAddressRequest address;
    private String imageUrl;
}
