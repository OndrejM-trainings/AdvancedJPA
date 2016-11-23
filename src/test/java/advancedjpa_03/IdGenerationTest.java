package advancedjpa_03;

import common.*;
import exercise03.*;
import javax.inject.Inject;
import javax.transaction.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

@RunWith(Arquillian.class)
public class IdGenerationTest {

    @Inject
    private PersonFactory personFactory;
    
    @Inject
    private UserTransaction ut;
    
    @Deployment()
    public static JavaArchive createDeployment() {
        return TstDeployment.createCommonJarDeployment("03");
    }

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
        ut.begin();
        final PersonWithIdUnspecified mrSmith = personFactory.createPerson("John", "Smith", 45);
        Assert.assertNotNull("Mr Smith does not exist", mrSmith);
        Assert.assertNotNull("Mr Smith does not have id", mrSmith.getId());
        ut.rollback();
    }
}
