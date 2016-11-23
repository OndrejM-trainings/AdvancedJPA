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
        p.setChildren(someChildren());
        
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

    private Set<Person> someChildren() {
        final List<Person> children = Arrays.asList(
                new Person("Jane", "Smith", 10),
                new Person("Mike", "Smith", 8),
                new Person("Paul", "Smith", 5));
        for (Person p : children) {
            em.persist(p);
        }
        return new HashSet<>(children);
    }

}
