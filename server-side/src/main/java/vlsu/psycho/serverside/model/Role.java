package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity(name = "roles")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Role extends BaseEntity{
    @Column(name = "title")
    @Enumerated(value = EnumType.STRING)
    private RoleTitle title;
}
