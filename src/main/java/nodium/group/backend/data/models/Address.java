package nodium.group.backend.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table
@Entity
@Setter
@Getter
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String state;
    private String lga;
    private String houseNumber;
    private String street;
}
