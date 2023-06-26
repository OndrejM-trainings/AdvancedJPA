package advancedjpa_01;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import entities.Person;
import exercise01.PersonService;

import static org.junit.jupiter.api.Assertions.fail;

import common.InTransaction;
import common.JPAProducer;
import common.TestData;
import common.WeldTestBase;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.hibernate.LazyInitializationException;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
@EnableAutoWeld
@AddBeanClasses({JPAProducer.class, PersonService.class})
public class LazyLoadingTest extends WeldTestBase {

    @Inject
    private PersonService personService;

    @Inject
    private TestData testData;

    @Inject
    private EntityManager em;

    @BeforeEach
    public void init() throws Exception {
        startRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            testData.initData();
        });
        stopRequest();
    }

    @Test
    public void should_have_person_with_children() {
        startRequest();
        final Person mrSmith = personService.findPersonByName("John", "Smith");
        stopRequest();

        try {
            assertThat("Number of children",
                    mrSmith.getChildren().size(), is(equalTo(3)));
        } catch (LazyInitializationException e) {
            fail("Children collection not read from the database");
        }
    }
}
