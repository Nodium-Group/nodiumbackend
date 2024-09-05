package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.AUTO;

@Table
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tokens {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long tokenId;
    private String token;
    @OneToOne
    private User user;
    private boolean isBlackListed;
}
