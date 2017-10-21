package entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CITIZEN")
public class Citizen extends AbstractPerson {
    private String nationalIdNumber;

    public Citizen() {
    }

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
