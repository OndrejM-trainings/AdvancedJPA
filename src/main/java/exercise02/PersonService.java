package exercise02;

import entities.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PersonService {

    @Inject
    private EntityManager em;
    
    public PersonWithoutDetails findPersonByName(String firstName, String surname) {
        return em.createQuery("select p from PersonWithoutDetails p where p.firstName = :firstName and p.surname = :surname", 
                PersonWithoutDetails.class)
                .setParameter("firstName", firstName)
                .setParameter("surname", surname)
                .getSingleResult();
    }
    
}
