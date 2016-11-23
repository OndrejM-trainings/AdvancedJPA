package advancedjpa_01;

import common.JPAProducer;
import entities.Person;
import exercise01.PersonService;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

@RunWith(Arquillian.class)
public class LazyLoadingTest {

    @Inject
    private PersonService personService;
    
    @Deployment()
    public static JavaArchive createDeployment() {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addAsManifestResource("META-INF/persistence.xml",
                        "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                // test packages
                .addPackages(true, LazyLoadingTest.class.getPackage())
                // code packages
                .addPackages(true, PersonService.class.getPackage())
                // common packages
                .addPackages(true, JPAProducer.class.getPackage());
    }

    @Test
    public void should_be_deployed() {
        final Person mrSmith = personService.findPersonBySurname("Smith");
        Assert.assertNotNull("Mr Smith exists", mrSmith);
        Assert.assertEquals("Number of children", 3, mrSmith.getChildren().size());
    }
}
