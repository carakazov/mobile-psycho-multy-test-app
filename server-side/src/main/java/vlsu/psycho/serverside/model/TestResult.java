package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "test_results")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestResult extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "min_border")
    private double minBorder;

    @Column(name = "max_border")
    private double maxBorder;
}
