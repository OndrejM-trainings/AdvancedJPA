package common;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JPAProducer {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    
    @Produces
    @RequestScoped
    public EntityManager getEm() {
        return createEm();
    }
    
    private EntityManager createEm() {
        return emf.createEntityManager();
    }
    
    
}
