package common;

import entities.*;
import java.util.*;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TestData {

    @Inject
    private Instance<EntityManager> emProvider;
    private EntityManager em;

    public void initData() throws Exception {
        em = emProvider.get();
        InTransaction.executeAndCloseEm(em, () -> {
            initMrSmith();
        });
        em = null;
    }

    private void initMrSmith() {
        Person p = new Person("John", "Smith", 45);
        p.setHomeAddress(anAddress());
        p.setChildren(someChildren(p.getSurname()));
        p.getChildren().iterator().next().setChildren(someChildren(p.getSurname() + "-grand"));

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
        em = emProvider.get();
        InTransaction.executeAndCloseEm(em, () -> {
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
        });
        em = null;
    }

}
