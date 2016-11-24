package advancedjpa_01;

import common.*;
import entities.Person;
import exercise01.PersonService;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

@RunWith(Arquillian.class)
public class LazyLoadingTest {

    @Inject
    private PersonService personService;
    
    @Inject 
    private TestData testData;
    
    @Deployment()
    public static JavaArchive createDeployment() {
        return TstDeployment.createCommonJarDeployment("01");
    }

    @Before
    public void init() throws Exception {
        testData.initData();
    }
    
    /*
     * TODO: Opravit test.
     * 
     * Test zlyha kvoli tomu, ze collection children nie je nacitana z databaze po ukonceni tranzakcie. 
     * Upravte chovanie personService, aby nacitala z databaze celu kolekciu children. 
     *
     * Viac sposobov riesenia:
     *  - manualne nacitanie kolekcie pred ukoncenim tranzakcie
     *  - pouzitie Fetch Join pre nacitanie children v query
     *  - zmena navratovej hodnoty z personService z entity Person na transfer objekt PersonDTO
     */
    
    @Test
    public void should_have_person_with_children() {
        final Person mrSmith = personService.findPersonByName("John", "Smith", "children");
        Assert.assertNotNull("Mr Smith does not exist", mrSmith);
        try {
            Assert.assertEquals("Number of children", 3, mrSmith.getChildren().size());
        } catch (Exception e) {
            if ("LazyInitializationException".equals(e.getClass().getSimpleName())) {
                throw new AssertionError("Children collection not read from the database", e);
            } else {
                throw e;
            }
        }
    }
}
