package nodium.group.backend.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FirmRegisterRequest {
    private String firmname;
    private String decrciption;
    private String details;
    private File file;
}
