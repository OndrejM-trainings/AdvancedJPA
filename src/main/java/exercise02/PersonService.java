package exercise02;

import exercise01.*;
import entities.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PersonService {

    @Inject
    private EntityManager em;
    
    public Person findPersonByName(String firstName, String surname) {
        return em.createQuery("select p from Person p where p.firstName = :firstName and p.surname = :surname", Person.class)
                .setParameter("firstName", firstName)
                .setParameter("surname", surname)
                .getSingleResult();
    }
    
}
