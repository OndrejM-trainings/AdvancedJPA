package entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CITIZEN")
public class Citizen extends Person {
    private String nationalIdNumber;

    public Citizen(String firstName, String surname, int age) {
        super(firstName, surname, age);
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }
    
    
}
