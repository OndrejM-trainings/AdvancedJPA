package advancedjpa_06;

import common.*;
import entities.Person;
import exercise01.PersonService;
import java.util.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;

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
@RunWith(CdiRunner.class)
@AdditionalClasses(JPAProducer.class)
public class ConverterTest {

    @Inject
    private PersonService personService;

    @Inject
    private TestData testData;

    @Inject
    private ContextController contextController;

    @Inject
    private EntityManager em;

    @Before
    public void init() throws Exception {
        contextController.openRequest();
        InTransaction.executeAndCloseEm(em, () -> {
            testData.initData();
        });
        contextController.closeRequest();
    }

    @Test
    public void should_have_person_with_children() {
        contextController.openRequest();
        personService.setBirthYearToPerson(getBirthDate(), "John", "Smith");
        contextController.closeRequest();

        contextController.openRequest();
        Person mrSmith = personService.findPersonByName("John", "Smith");
        Assert.assertEquals("Year of birth as string should be just year of birth", 
                "2000", mrSmith.getYearOfBirthAsString());
        contextController.closeRequest();
    }

    private Date getBirthDate() {
        final Calendar cal = Calendar.getInstance();
        cal.set(2000, 1, 1);
        return cal.getTime();
    }
}
