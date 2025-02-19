package nodium.group.backend.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequest {
    private String userEmail;
    private String providerEmail;
    private String review;
}
