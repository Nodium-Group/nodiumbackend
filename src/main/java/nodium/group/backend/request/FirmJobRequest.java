package nodium.group.backend.request;

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
