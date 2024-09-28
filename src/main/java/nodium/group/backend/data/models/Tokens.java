package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;
    private String token;
    @OneToOne
    private User user;
    private boolean isBlackListed;
}
