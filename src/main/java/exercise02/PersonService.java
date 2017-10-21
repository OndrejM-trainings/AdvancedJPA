package exercise02;

import common.InTransaction;
import entities.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class PersonService {

    @Inject
    private EntityManager em;

    public PersonWithoutDetails findPersonByName(String firstName, String surname) {
        return InTransaction.executeAndCloseEm(em, () -> {
            final Person p = em.createQuery("select p from Person p where p.firstName = :firstName and p.surname = :surname", Person.class)
                    .setParameter("firstName", firstName)
                    .setParameter("surname", surname)
                    .getSingleResult();
            return new PersonWithoutDetails(p.getFirstName(), p.getSurname(), p.getAge());
        });
    }

}
