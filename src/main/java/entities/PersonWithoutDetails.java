package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PERSONS")
public class PersonWithoutDetails extends AbstractPerson implements Serializable {


    public PersonWithoutDetails() {
    }

    public PersonWithoutDetails(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }


    
}
