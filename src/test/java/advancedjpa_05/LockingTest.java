package advancedjpa_05;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import common.InTransaction;
import common.JPAProducer;
import common.TestData;
import common.WeldTestBase;
import entities.Person;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
@EnableAutoWeld
@AddBeanClasses(JPAProducer.class)
public class LockingTest extends WeldTestBase {

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

    @Inject
    private Transaction1 transaction1;

    @Inject
    private Transaction2 transaction2;

    @BeforeEach
    public void init() throws Exception {
        startRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            testData.initData();
            testData.initDataPersons();
        });
        stopRequest();
    }

    @Test
    public void update_in_parallel() throws NotSupportedException, SystemException, InterruptedException {
        startRequest();
        Thread tx1 = new Thread(() -> {
            startRequest();
            transaction1.run();
            stopRequest();
        });

        Thread tx2 = new Thread(() -> {
            startRequest();
            transaction2.run();
            stopRequest();
        });

        tx1.start();
        tx2.start();
        tx1.join();
        tx2.join();
        InTransaction.executeAndCloseEm(em, () -> {
            final Person person = testData.getJohnSmith();
            assertThat("Data from transaction 1 overwriten by transaction 2", person.getNotes(), is(equalTo("tx1")));
        });
        stopRequest();
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
            Thread.sleep(i);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }

}
