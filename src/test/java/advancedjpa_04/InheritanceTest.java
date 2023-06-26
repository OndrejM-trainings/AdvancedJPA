package advancedjpa_04;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import common.InTransaction;
import common.JPAProducer;
import common.TestData;
import common.WeldTestBase;
import entities.AbstractPerson;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import java.util.List;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * TODO: Vyskusat rozne typy dedicnosti
 * 
 * Viac sposobov riesenia dedicnosti:
 *  - TABLE_PER_CLASS
 *  - JOIN
 *  - SINGLE_TABLE
 * 
 */
@EnableAutoWeld
@AddBeanClasses(JPAProducer.class)
public class InheritanceTest extends WeldTestBase {

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

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
    public void should_load_persons() throws NotSupportedException, SystemException {
        startRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            final List<AbstractPerson> people
                    = em.createQuery("select p from AbstractPerson p",
                            AbstractPerson.class).getResultList();

            assertThat("People shouldn't be empty", people, is(not(empty())));
        });
        stopRequest();

    }
}
