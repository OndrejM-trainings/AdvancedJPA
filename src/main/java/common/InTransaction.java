package common;

import jakarta.persistence.EntityManager;
import java.util.concurrent.Callable;

public class InTransaction {

    public static void executeAndCloseEm(EntityManager em, Runnable runnable) {
        executeAndCloseEmOptionally(em, runnable, true);
    }
    
    public static void execute(EntityManager em, Runnable runnable) {
        executeAndCloseEmOptionally(em, runnable, false);
    }
    
    private static void executeAndCloseEmOptionally(EntityManager em, Runnable runnable, boolean close) {
        InTransaction.executeAndCloseEmOptionally(em, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                runnable.run();
                return null;
            }
        }, close);
    }
    
    public static <V> V executeAndCloseEm(EntityManager em, Callable<V> callable) {
        return executeAndCloseEmOptionally(em, callable, true);
    }
    
    private static <V> V executeAndCloseEmOptionally(EntityManager em, Callable<V> callable, boolean close) {
        em.getTransaction().begin();
        try {
            return callable.call();
        } catch (Exception e) {
            em.getTransaction().rollback();
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            if (em.getTransaction().isActive() && !em.getTransaction().getRollbackOnly()) {
                em.getTransaction().commit();
            }
            if (close) em.close();
        }
    }
}
