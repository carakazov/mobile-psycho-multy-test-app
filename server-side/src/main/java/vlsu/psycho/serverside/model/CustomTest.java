package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "custom_tests")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomTest extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "allowed_users",
            joinColumns = @JoinColumn(name = "custom_test_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> allowedUsers;
}
