package entities;

import exercise06.BirthYearConverter;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1;
    
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    private String firstName;
    private String surname;
    private int age;
    private String notes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date yearOfBirth;
    @Column(name = "yearOfBirth", updatable = false, insertable = false, nullable = false)
    private String yearOfBirthAsString;

    public Person() {
    }

    public Person(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }
    
    @OneToMany(fetch = LAZY)
    private Set<Person> children; 
    
    @OneToOne
    private Address homeAddress;

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

    public Set<Person> getChildren() {
        return children;
    }

    public void setChildren(Set<Person> children) {
        this.children = children;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Date yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getYearOfBirthAsString() {
        return yearOfBirthAsString;
    }

}
