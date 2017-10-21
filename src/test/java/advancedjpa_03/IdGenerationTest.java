package advancedjpa_03;

import common.InTransaction;
import common.*;
import exercise03.*;
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
public class IdGenerationTest {

    @Inject
    private PersonFactory personFactory;

    @Inject
    private EntityManager em;

    @Before
    public void init() throws Exception {
    }

    /*
     * TODO: Vyskusat rozne typy strategii generovania.
     * 
     * Test zlyha kvoli tomu, ze pri volani em.persist nie je nastavene id.
     *
     * Viac sposobov riesenia:
     *  - nastavit id v kode pred volanim em.persist
     *  - pouzit generovanie id typu IDENTITY, SEQUENCE alebo TABLE pre PersonWithIdUnspecified
     * 
     * Pozri EntityWithIdIdentity, EntityWithIdSequence a EntityWithIdTable
     */
    @Test
    public void should_create_person_and_get_id() throws NotSupportedException, SystemException {
        InTransaction.executeAndCloseEm(em, () -> {
            final PersonWithIdUnspecified mrSmith = personFactory.createPerson("John", "Smith", 45);
            Assert.assertNotNull("Mr Smith does not exist", mrSmith);
            Assert.assertNotNull("Mr Smith does not have id", mrSmith.getId());
        });
    }
}
