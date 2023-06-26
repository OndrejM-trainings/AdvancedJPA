package entities;

import jakarta.persistence.Entity;

@Entity
public class Foreigner extends AbstractPerson {
    private String countryOfCitizenship;

    public Foreigner() {
    }

    public Foreigner(String firstName, String surname, int age) {
        super(firstName, surname, age);
    }

    public String getCountryOfCitizenship() {
        return countryOfCitizenship;
    }

    public void setCountryOfCitizenship(String countryOfCitizenship) {
        this.countryOfCitizenship = countryOfCitizenship;
    }
    
    
}
