package exercise03;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.io.Serializable;

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
