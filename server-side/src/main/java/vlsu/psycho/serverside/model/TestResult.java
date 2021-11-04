package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Data
@Entity(name = "test_results")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestResult extends BaseEntity {
    private String value;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
