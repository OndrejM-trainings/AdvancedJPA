package exercise03;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Dependent
public class PersonFactory {

    @Inject
    private EntityManager em;

    public PersonWithIdUnspecified createPerson(String firstName, String lastName, int age) {
        final PersonWithIdUnspecified p = new PersonWithIdUnspecified(firstName, lastName, age);
        em.persist(p);
        return p;
    }

}
