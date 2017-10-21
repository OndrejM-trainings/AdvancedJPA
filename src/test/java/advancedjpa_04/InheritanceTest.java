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

@RunWith(CdiRunner.class)
@AdditionalClasses(JPAProducer.class)
public class InheritanceTest {

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

    @Before
    public void init() throws Exception {
        testData.initData();
        testData.initDataPersons();
    }

    /*
     * TODO: Vyskusat rozne typy dedicnosti
     * 
     * Viac sposobov riesenia dedicnosti:
     *  - TABLE_PER_CLASS
     *  - JOIN
     *  - SINGLE_TABLE
     * 
     * Namapuj 
     */
    @Test
    public void should_load_persons() throws NotSupportedException, SystemException {
        InTransaction.executeAndCloseEm(em, () -> {
            final List<AbstractPerson> people
                    = em.createQuery("select p from AbstractPerson p",
                            AbstractPerson.class).getResultList();

            Assert.assertTrue("People empty", !people.isEmpty());
        });

    }
}
