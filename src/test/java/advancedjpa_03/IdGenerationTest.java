package advancedjpa_03;

import common.*;
import exercise03.*;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    
    @Deployment()
    public static JavaArchive createDeployment() {
        return TstDeployment.createCommonJarDeployment("03");
    }

    @Before
    public void init() throws Exception {
    }
    
    /*
     * TODO: Opravit test.
     * 
     * Test zlyha kvoli tomu, po volani metody createPerson este neexistuje nijake id pre vytvorenu osobu.
     *
     * Viac sposobov riesenia:
     *  - zavolat createPerson v novej tranzakcii (REQUIRES_NEW) s akymkolvek generovanim id pre PersonWithIdUnspecified
     *  - pouzit generovanie id typu SEQUENCE alebo TABLE pre PersonWithIdUnspecified
     * 
     * Pozri EntityWithIdIdentity, EntityWithIdSequence a EntityWithIdTable
     */
    
    @Test
    @Transactional
    public void should_create_person_and_get_id() {
        final PersonWithIdUnspecified mrSmith = personFactory.createPerson("John", "Smith", 45);
        Assert.assertNotNull("Mr Smith does not exist", mrSmith);
        Assert.assertNotNull("Mr Smith does not have id", mrSmith.getId());
    }
}
