package nodium.group.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    private Long userId;
    private String location;
    private String description;
    private String name;
    private BigDecimal amount;
    private String category;
}
