package advancedjpa_02;

import common.*;
import entities.*;
import exercise02.PersonService;
import javax.inject.Inject;
import org.jglue.cdiunit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

@RunWith(CdiRunner.class)
@AdditionalClasses(JPAProducer.class)
public class WithoutDetailsTest {

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
     * Test zlyha kvoli tomu, ze v priebehu nacitania dat pre PersonWithoutDetails bola nacitana aspon jedna entita Address. 
     * Bolo to kvoli tomu, ze personService nacita najprv entitu Person, co automaticky nacita Address v poli homeAdress, ktore je by default Eager.
     *
     * Viac sposobov riesenia:
     *  - nastavit relaciu Person.homeAddress na Lazy
     *  - vytvorit spolocneho predka pre Person a PersonWithoutDetails ako MappedSuperclass, ktora obsahuje iba eager nacitavane polia. 
              Person bude obsahovat navyse ostatne polia, ale PersonWithoutDetails uz nic navyse obsahovat nebude. PersonService bude nacitat iba entitu PersonWithoutDetails, bez pola homeAddress.
     */
    
    @Test
    public void should_have_person_without_loading_address() {
        final PersonWithoutDetails mrSmith = personService.findPersonByName("John", "Smith");
        Assert.assertNotNull("Mr Smith does not exist", mrSmith);
        Assert.assertEquals("Number of loaded addresses", 0, Address.countLoaded);
    }
}
