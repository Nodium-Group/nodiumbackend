package nodium.group.backend.dto.out;

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
