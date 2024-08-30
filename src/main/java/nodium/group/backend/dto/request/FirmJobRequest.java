package nodium.group.backend.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FirmJobRequest {
    private long id;
    private JobRequest JobRequest;
    private Integer numberOfPeopleNeeded;
}
