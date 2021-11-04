package vlsu.psycho.serverside.model;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
}
