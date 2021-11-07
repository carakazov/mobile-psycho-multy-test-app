package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "tests")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Test extends BaseEntity {
    @Column(name = "external_id")
    private UUID externalId = UUID.randomUUID();
    private String title;
    @Column(name = "gender_depending")
    private Boolean isGenderDepending;
    @Column(name = "re_answer_enabled")
    private Boolean isReAnswerEnabled;
    @Column(name = "proceeding_type")
    private ProceedingType proceedingType;
    @Column(name = "expected_time")
    private String expectedTime;
    @OneToMany(mappedBy = "test")
    private List<Question> questions;
    @OneToMany(mappedBy = "test")
    private List<Text> descriptions;
    @OneToMany(mappedBy = "test")
    private List<TestResult> results;
}
