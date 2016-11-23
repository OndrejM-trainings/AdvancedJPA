package common;

import javax.persistence.*;

public class BaseJPA {

    void startTransaction() {
        
    }

    void stopTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static final class JPAContext {

        private EntityManagerFactory factory;

        private JPAContext() {
        }

    }

    protected static JPAContext initialize() {
        JPAContext context = new JPAContext();
        context.factory = Persistence.createEntityManagerFactory("test");
        return context;
    }

}
