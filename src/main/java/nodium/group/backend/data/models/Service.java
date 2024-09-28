package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        @ManyToOne(optional = true , cascade = CascadeType.ALL)
        private User provider;
        @ManyToOne(optional = true , cascade = CascadeType.ALL)
        private User poster;
        private String location;
        @Setter(AccessLevel.NONE)
        private LocalDateTime timeStamp= now();
        private boolean isDeleted;
}