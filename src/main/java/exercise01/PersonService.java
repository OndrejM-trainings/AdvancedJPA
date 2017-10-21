package exercise01;

import common.InTransaction;
import entities.Person;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PersonService {

    @Inject
    private EntityManager em;

    public Person findPersonByName(String firstName, String surname) {
        return InTransaction.executeAndCloseEm(em, () -> {

            return em.createQuery("select p from Person p where p.firstName = :firstName and p.surname = :surname", Person.class)
                    .setParameter("firstName", firstName)
                    .setParameter("surname", surname)
                    .getSingleResult();
        });
    }

}
