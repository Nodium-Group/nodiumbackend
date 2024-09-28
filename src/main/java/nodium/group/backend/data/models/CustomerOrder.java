package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.*;
import nodium.group.backend.data.enums.OrderStatus;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeStamp;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    private User provider;
    @OneToOne
    private User customer;
    private LocalDateTime timeUpdated= now();
    @PreUpdate
    private void setTimeUpdated(){
        timeUpdated = LocalDateTime.now();
    }
}