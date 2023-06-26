package exercise03;

import static jakarta.persistence.GenerationType.TABLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;
import java.io.Serializable;

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
