package entities;

import javax.persistence.*;

@MappedSuperclass
public class AbstractPerson {
    @Id
    @GeneratedValue
    private Long id;
    
    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    protected int age;
    protected String firstName;
    protected String surname;

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
