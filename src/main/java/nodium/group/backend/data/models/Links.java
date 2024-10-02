package nodium.group.backend.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table
@Entity
public class Links {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;
    private String githubLink;
    private String linkedinLink;
}
