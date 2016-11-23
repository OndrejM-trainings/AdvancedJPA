package entities;

import java.io.Serializable;

public class PersonWithoutDetails implements Serializable {

    private String firstName;
    private String surname;
    private int age;

    public PersonWithoutDetails() {
    }

    public PersonWithoutDetails(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
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
