package common;

import entities.*;
import java.util.*;
import javax.ejb.*;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
@TransactionAttribute(REQUIRES_NEW)
public class TestData {

    @Inject
    private EntityManager em;

    public void initData() throws Exception {
        initMrSmith();
    }

    private void initMrSmith() {
        Person p = new Person("John", "Smith", 45);
        p.setHomeAddress(anAddress());
        p.setChildren(someChildren(p.getSurname()));
        
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

    public void initDataPersons() {
        Citizen p = new Citizen("John", "Kane", 45);
        p.setHomeAddress(anAddress());
        p.setChildren(someChildren(p.getSurname()));
        p.setNationalIdNumber("XA1254");
        em.persist(p);

        Foreigner f = new Foreigner("John", "Smith", 45);
        f.setHomeAddress(anAddress());
        f.setChildren(someChildren(p.getSurname()));
        f.setCountryOfCitizenship("Canada");
        em.persist(f);
    }

}
