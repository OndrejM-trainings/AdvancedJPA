package exercise03;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.*;

@Entity
@TableGenerator(name = "myTable", table = "T_ID_GENERATOR", allocationSize = 100)
public class EntityWithIdTable implements Serializable {

    @Id
    @GeneratedValue(strategy = TABLE, generator = "myTable")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
