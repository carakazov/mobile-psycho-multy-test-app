package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity{
    private String name;
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column(name = "external_id")
    private UUID externalId = UUID.randomUUID();
    private String password;
    private String login;
    private String email;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    private User psychologist;

    @OneToMany(mappedBy = "psychologist")
    private List<User> clients;

    @ManyToMany(mappedBy = "allowedUsers")
    private List<CustomTest> allowedTest;

    @OneToMany(mappedBy = "user")
    private List<TakenTest> takenTests;
}
