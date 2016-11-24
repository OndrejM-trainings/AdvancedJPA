package exercise03;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.GenerationType.TABLE;

@Entity
@TableGenerator(name = "tableGen", table = "t_generator")
public class PersonWithIdUnspecified implements Serializable {

    @Id
    @GeneratedValue(strategy = TABLE, generator = "tableGen")
    private Long id;
    
    private String firstName;
    private String surname;
    private int age;

    public PersonWithIdUnspecified() {
    }

    public PersonWithIdUnspecified(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
