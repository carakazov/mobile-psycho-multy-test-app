package vlsu.psycho.serverside.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
}
