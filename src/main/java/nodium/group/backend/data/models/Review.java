package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeSaved = now();
    private String reviewContent;
    @ManyToOne
    private User reviewer;
    @ManyToOne
    private User reviewee;



}
