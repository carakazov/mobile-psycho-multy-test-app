package vlsu.psycho.serverside.model;

import liquibase.pro.packaged.E;
import liquibase.pro.packaged.J;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity(name = "taken_tests")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TakenTest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @ManyToOne
    @JoinColumn(name = "result_id")
    private TestResult result;

    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "finish_date")
    private LocalDateTime finishDate;
}
