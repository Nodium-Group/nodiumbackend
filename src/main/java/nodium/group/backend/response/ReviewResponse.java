package nodium.group.backend.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReviewResponse {
    private Long id;
    private String revieweeEmail;
    private String content;

}
