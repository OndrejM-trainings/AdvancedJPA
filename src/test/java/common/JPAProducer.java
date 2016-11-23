package common;

import javax.enterprise.context.*;
import javax.enterprise.inject.Produces;
import javax.persistence.*;

@Dependent
public class JPAProducer {
    @PersistenceContext(name = "test")
    @Produces
    public EntityManager em;
}
