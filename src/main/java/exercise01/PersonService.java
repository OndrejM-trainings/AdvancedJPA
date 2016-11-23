package exercise01;

import entities.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PersonService {

    @Inject
    private EntityManager em;
    
    public Person findPersonBySurname(String surname) {
        return em.createQuery("select p from Person p where p.surname = :surname", Person.class)
                .setParameter("surname", surname)
                .getSingleResult();
    }
    
}
