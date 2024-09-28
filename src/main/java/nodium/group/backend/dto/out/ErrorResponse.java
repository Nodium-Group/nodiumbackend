package nodium.group.backend.dto.out;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private String errorMessage;
    private String errorPath;
}
