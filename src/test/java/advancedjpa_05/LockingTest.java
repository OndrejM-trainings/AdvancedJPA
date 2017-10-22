package advancedjpa_05;

import common.InTransaction;
import common.*;
import entities.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.*;
import org.jglue.cdiunit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

/*
 * TODO: Zabranit prepisaniu data z tranzakcie 1 tranzakciou 2
 *
 * Test zlyha, pretoze transakcia 1 zapise svoje zmeny a po nej transakcia 2 zapise zmeny, ktore ich ignoruju.
 * Tranzakcia 2 tak prepise tranzakciu 1 bez akejkolvek kontroly konzistenie dat.
 * 
 * Viac sposobov riesenia:
 *  - pesimisticky zamok
 *  - optimisticky zamok a chyba pri zlyhani zapisu
 *  - optimisticky zamok a restart tranzakcie pri zlyhani zapisu
 * 
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({JPAProducer.class, LockingTest.Transaction1.class, LockingTest.Transaction2.class})
public class LockingTest {

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

    @Inject
    private ContextController contextController;

    @Inject
    private Transaction1 transaction1;

    @Inject
    private Transaction2 transaction2;

    @Before
    public void init() throws Exception {
        contextController.openRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            testData.initData();
            testData.initDataPersons();
        });
        contextController.closeRequest();
    }

    @Test
    public void update_in_parallel() throws NotSupportedException, SystemException, InterruptedException {
        contextController.openRequest();
        Thread tx1 = new Thread(() -> {
            contextController.openRequest();
            transaction1.run();
            contextController.closeRequest();
        });

        Thread tx2 = new Thread(() -> {
            contextController.openRequest();
            transaction2.run();
            contextController.closeRequest();
        });

        tx1.start();
        tx2.start();
        tx1.join();
        tx2.join();
        InTransaction.executeAndCloseEm(em, () -> {
            final Person person = testData.getJohnSmith();
            Assert.assertEquals("Data from transaction 1 overwriten by transaction 2", "tx1", person.getNotes());
        });
        contextController.closeRequest();
    }

    @RequestScoped
    public static class Transaction1 implements Runnable {

        @Inject
        private EntityManager em;

        @Inject
        private TestData testData;

        @Override
        public void run() {
            InTransaction.executeAndCloseEm(em, () -> {
                final Person person = testData.getJohnSmith();
                processTransaction1();
                person.setNotes(person.getNotes() + "tx1");
            });
        }
    }

    @RequestScoped
    public static class Transaction2 implements Runnable {

        @Inject
        private EntityManager em;

        @Inject
        private TestData testData;

        @Override
        public void run() {
            InTransaction.executeAndCloseEm(em, () -> {
                final Person person = testData.getJohnSmith();
                processTransaction2();
                person.setNotes(person.getNotes() + "tx2");
            });
        }
    }

    private static void processTransaction1() {
        sleep(200);
    }

    private static void processTransaction2() {
        sleep(1000);
    }

    private static void sleep(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }

}
