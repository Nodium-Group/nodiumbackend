package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Setter(NONE)
    private LocalDateTime timeSaved;
    private String reviewContent;
    @OneToOne
    private User reviewer;
    @OneToOne
    private User reviewee;
    @PrePersist
    void persist(){
        timeSaved=LocalDateTime.now();
    }


}
