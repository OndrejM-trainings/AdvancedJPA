package advancedjpa_01;

import common.*;
import entities.Person;
import exercise01.PersonService;
import javax.inject.Inject;
import org.jglue.cdiunit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(JPAProducer.class)
public class LazyLoadingTest {

    @Inject
    private PersonService personService;

    @Inject
    private TestData testData;


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
        final Person mrSmith = personService.findPersonByName("John", "Smith");
        try {
            Assert.assertEquals("Number of children", 3, mrSmith.getChildren().size());
        } catch (Exception e) {
            if ("LazyInitializationException".equals(e.getClass().getSimpleName())) {
                fail("Children collection not read from the database");
            } else {
                throw e;
            }
        }
    }
}
