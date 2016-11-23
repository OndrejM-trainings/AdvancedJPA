package common;

import javax.persistence.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ondro
 */
public class LazyLoading extends BaseJPA {

    private static JPAContext jpaContext;
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @BeforeClass
    public static void setUpTests() {
        jpaContext = initialize();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        super.startTransaction();
    }

    @After
    public void tearDown() {
        super.stopTransaction();
    }

}
