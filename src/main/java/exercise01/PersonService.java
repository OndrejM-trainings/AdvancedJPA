package exercise01;

import common.InTransaction;
import entities.Person;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Date;

public class PersonService {

    @Inject
    private EntityManager em;

    public Person findPersonByName(String firstName, String surname) {
        return InTransaction.executeAndCloseEm(em, () -> {
            return queryPersonByName(firstName, surname);
        });
    }

    private Person queryPersonByName(String firstName, String surname) {
        return em.createQuery("select p from Person p where p.firstName = :firstName and p.surname = :surname", Person.class)
                .setParameter("firstName", firstName)
                .setParameter("surname", surname)
                .getSingleResult();
    }

    public void setBirthYearToPerson(Date birthDate, String firstName, String surname) {
        InTransaction.executeAndCloseEm(em, () -> {

            Person personToModify = queryPersonByName(firstName, surname);
            personToModify.setYearOfBirth(birthDate);
        });
    }

}
