package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.NONE;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jobs")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal amount;
    private String category;
    @ManyToOne(optional = true ,fetch = LAZY)
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
