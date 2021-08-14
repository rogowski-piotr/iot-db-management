package pl.piotr.iotdbmanagement.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.role.Role;


import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "users_id_generator", sequenceName = "users_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

}
