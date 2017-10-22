package advancedjpa_04;

import common.InTransaction;
import common.*;
import entities.*;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.*;
import org.jglue.cdiunit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

/*
 * TODO: Vyskusat rozne typy dedicnosti
 * 
 * Viac sposobov riesenia dedicnosti:
 *  - TABLE_PER_CLASS
 *  - JOIN
 *  - SINGLE_TABLE
 * 
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(JPAProducer.class)
public class InheritanceTest {

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

    @Inject
    private ContextController contextController;

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
    @InRequestScope
    public void should_load_persons() throws NotSupportedException, SystemException {
        InTransaction.executeAndCloseEm(em, () -> {
            final List<AbstractPerson> people
                    = em.createQuery("select p from AbstractPerson p",
                            AbstractPerson.class).getResultList();

            Assert.assertTrue("People shouldn't be empty", !people.isEmpty());
        });

    }
}
