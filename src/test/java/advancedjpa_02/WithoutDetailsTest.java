package advancedjpa_02;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import common.*;
import entities.*;
import exercise02.PersonService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * TODO: Opravit test.
 * 
 * Test zlyha kvoli tomu, ze v priebehu nacitania dat pre PersonWithoutDetails bola nacitana aspon jedna entita Address. 
 * Bolo to kvoli tomu, ze personService nacita najprv entitu Person, co automaticky nacita Address v poli homeAdress, ktore je by default Eager.
 *
 * Viac sposobov riesenia:
 *  - nastavit relaciu Person.homeAddress na Lazy
 *  - vytvorit spolocneho predka pre Person a PersonWithoutDetails ako MappedSuperclass, ktora obsahuje iba eager nacitavane polia. 
          Person bude obsahovat navyse ostatne polia, ale PersonWithoutDetails uz nic navyse obsahovat nebude. PersonService bude nacitat iba entitu PersonWithoutDetails, bez pola homeAddress.
 */
@EnableAutoWeld
@AddBeanClasses({JPAProducer.class, PersonService.class})
public class WithoutDetailsTest extends WeldTestBase {

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
    public void should_have_person_without_loading_address() {
        startRequest();
        final PersonWithoutDetails mrSmith = personService.findPersonByName("John", "Smith");
        stopRequest();

        assertThat("Mr Smith does not exist", mrSmith, is((not(nullValue()))));
        assertThat("Number of loaded addresses", Address.countLoaded, is(equalTo(0)));

    }
}
