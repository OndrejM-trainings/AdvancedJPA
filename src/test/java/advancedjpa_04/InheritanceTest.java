package advancedjpa_04;

import common.*;
import entities.Person;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

@RunWith(Arquillian.class)
public class InheritanceTest {

    @Inject 
    private TestData testData;
    
    @Inject
    private UserTransaction ut;
    
    @Deployment()
    public static JavaArchive createDeployment() {
        return TstDeployment.createCommonJarDeployment("03");
    }

    @Before
    public void init() throws Exception {
        testData.initData();
        testData.initDataPersons();
    }
    
    /*
     * TODO: Vyskusat rozne typy dedicnosti
     * 
     * Viac sposobov riesenia dedicnosti:
     *  - TABLE_PER_CLASS
     *  - JOIN
     *  - SINGLE_TABLE
     * 
     * Namapuj 
     */
    
    @Inject
    private EntityManager em;
    
    @Test
    public void should_load_persons() throws NotSupportedException, SystemException {
        ut.begin();
        
        final List<Person> people = 
                em.createQuery("select p from Person p", 
                        Person.class).getResultList();
        
        Assert.assertTrue("People empty", !people.isEmpty());
        ut.rollback();
    }
}
