package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "answered_questions")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnsweredQuestion extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "taken_test_id")
    private TakenTest takenTest;
}
