package exercise03;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.*;

@Entity
@SequenceGenerator(name = "mySequence", allocationSize = 100, sequenceName = "S_ID_GENERATOR")
public class EntityWithIdSequence implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "mySequence")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
