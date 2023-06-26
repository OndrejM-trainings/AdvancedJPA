package common;

import entities.AbstractPerson;
import entities.Address;
import entities.Citizen;
import entities.Foreigner;
import entities.Person;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestScoped
public class TestData {

    @Inject
    private EntityManager em;

    public void initData() {
        initMrSmith();
    }

    public Person getJohnSmith() {
        final Person result = em.createQuery(
                "select p from Person p where p.firstName = 'John' and p.surname = 'Smith' and p.age = 45",
                Person.class).getSingleResult();
        return result;
    }

    private void initMrSmith() {
        Person p = new Person("John", "Smith", 45);
        p.setHomeAddress(anAddress());
        p.setChildren(someChildren(p.getSurname()));
        p.getChildren().iterator().next().setChildren(someChildren(p.getSurname() + "-grand"));
        p.setNotes("");

        em.persist(p);
    }

    private Address anAddress() {
        final Address a = new Address();
        a.setStreet("Main Street");
        a.setNumber("1");
        a.setZipCode("94901");
        a.setCity("Nitra");
        a.setCountry("Slovakia");
        em.persist(a);
        return a;
    }

    private Set<Person> someChildren(String surname) {
        final List<Person> children = Arrays.asList(
                new Person("Jane", surname, 10),
                new Person("Mike", surname, 8),
                new Person("Paul", surname, 5));
        for (Person p : children) {
            em.persist(p);
        }
        return new HashSet<>(children);
    }

    private Set<AbstractPerson> someAbstractChildren(String surname) {
        final List<Citizen> children = Arrays.asList(
                new Citizen("Jane", surname, 10),
                new Citizen("Mike", surname, 8),
                new Citizen("Paul", surname, 5));
        for (Citizen p : children) {
            em.persist(p);
        }
        return new HashSet<>(children);
    }

    public void initDataPersons() {
        Citizen p = new Citizen("John", "Kane", 45);
        p.setHomeAddress(anAddress());
        p.setChildren(someAbstractChildren(p.getSurname()));
        p.setNationalIdNumber("XA1254");
        em.persist(p);

        Foreigner f = new Foreigner("John", "Smith", 45);
        f.setHomeAddress(anAddress());
        f.setChildren(someAbstractChildren(p.getSurname()));
        f.setCountryOfCitizenship("Canada");
        em.persist(f);
    }

}
