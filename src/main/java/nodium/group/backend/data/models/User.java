package nodium.group.backend.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nodium.group.backend.data.enums.Role;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Table(name="users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String password;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = ALL)
    private Address address;
    @OneToOne(cascade = ALL)
    private Details details;
    @OneToOne(cascade = ALL)
    private Links link;
}
