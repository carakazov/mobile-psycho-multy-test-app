package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "questions")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Question extends BaseEntity {
    @Column(name = "external_id")
    private UUID externalId = UUID.randomUUID();
    private byte[] picture;
    @Column(name = "multi_answer")
    private Boolean multiAnswer = Boolean.FALSE;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @OneToMany(mappedBy = "question")
    private List<Text> texts;
}
