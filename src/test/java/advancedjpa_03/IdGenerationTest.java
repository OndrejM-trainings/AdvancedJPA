package advancedjpa_03;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import common.InTransaction;
import common.JPAProducer;
import common.WeldTestBase;
import exercise03.PersonFactory;
import exercise03.PersonWithIdUnspecified;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
@EnableAutoWeld
@AddBeanClasses({JPAProducer.class, PersonFactory.class})
public class IdGenerationTest extends WeldTestBase {

    @Inject
    private PersonFactory personFactory;

    @Inject
    private EntityManager em;

    @BeforeEach
    public void init() throws Exception {
    }

    @Test
    public void should_create_person_and_get_id() throws NotSupportedException, SystemException {
        startRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            PersonWithIdUnspecified mrSmith = null;
            try {
                mrSmith = personFactory.createPerson("John", "Smith", 45);
            } catch (Exception e) {
                if (e.getCause() != null && "IdentifierGenerationException".equals(e.getCause().getClass().getSimpleName())) {
                    fail("mrSmith wasn't saved to the database - no ID assigned");
                } else {
                    throw e;
                }
            }
            assertThat("Mr Smith should exist", mrSmith, is((not(nullValue()))));
            assertThat("Mr Smith should have id", mrSmith.getId(), is((not(nullValue()))));
        });
    }
}
