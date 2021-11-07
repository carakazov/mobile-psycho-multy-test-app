package vlsu.psycho.serverside.model;

import liquibase.pro.packaged.E;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "answers")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Answer extends BaseEntity {
    private Double value;
    @Column(name = "external_id")
    private UUID externalId = UUID.randomUUID();
    private byte[] picture;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @OneToMany(mappedBy = "answer")
    private List<Text> texts;
}
