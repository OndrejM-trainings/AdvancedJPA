package advancedjpa_06;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import common.InTransaction;
import common.JPAProducer;
import common.TestData;
import common.WeldTestBase;
import entities.Person;
import exercise01.PersonService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * TODO: Opravit test.
 * 
 * Test zlyha kvoli tomu, ze datum narodenia je ulozeny ako datum, ale ma byt ulozeny ako rok v textovom formate.
 *
 * Viac sposobov riesenia:
 *  - urobit dateOfBirth premennu v Person ako transient a ukladat hodnotu ako text pomocou prepersist, postload a postupdate
 *  - pouzit konverter ktory bude prekladat Date na textovu hodnotu s rokom narodenia
 *
 * Mozeme este upravit typ yearOfBirth a zmenit ho z java.util.Date na java.time.Year
 */
@EnableAutoWeld
@AddBeanClasses({JPAProducer.class, PersonService.class})
public class ConverterTest extends WeldTestBase {

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
        personService.setBirthYearToPerson(getBirthDate(), "John", "Smith");
        stopRequest();

        startRequest();
        Person mrSmith = personService.findPersonByName("John", "Smith");
        assertThat("Year of birth as string should be just year of birth",
                mrSmith.getYearOfBirthAsString(), is(equalTo("2000")));
        stopRequest();
    }

    private Date getBirthDate() {
        final Calendar cal = Calendar.getInstance();
        cal.set(2000, 1, 1);
        return cal.getTime();
    }

}
