package common;

import javax.enterprise.context.*;
import javax.enterprise.inject.Produces;
import javax.persistence.*;

@ApplicationScoped
public class JPAProducer {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    
    @Produces
    @Dependent
    public EntityManager getEm() {
        return createEm();
    }
    
    private EntityManager createEm() {
        return emf.createEntityManager();
    }
    
    
}
