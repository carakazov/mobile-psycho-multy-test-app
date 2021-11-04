package vlsu.psycho.serverside.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity(name = "languages")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Language extends BaseEntity {
    private String code;
}
