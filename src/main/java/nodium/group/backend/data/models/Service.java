package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.NONE;
import static java.time.LocalDateTime.now;

@Entity
@Table(name = "services")
@Getter
@Setter
public class Service {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String description;
        private BigDecimal amount;
        private String category;
        @ManyToOne
        private User provider;
        @ManyToOne
        @JoinColumn(name="job_creator")
        private User poster;
        private String location;
        @Setter(NONE)
        private LocalDateTime timeStamp;
        @PrePersist
        private void setTimeStamp(){
                timeStamp= now();
        }
}