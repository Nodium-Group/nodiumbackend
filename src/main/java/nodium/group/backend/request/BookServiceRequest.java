package nodium.group.backend.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookServiceRequest {
    private Long id;
    private Long serviceId;
    private  String orderDescription;
    private Long userId;
    private LocalDateTime timeOrdered;
    private BigDecimal amount;
}