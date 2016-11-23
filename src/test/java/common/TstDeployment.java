package common;

import advancedjpa_01.LazyLoadingTest;
import entities.Person;
import exercise01.PersonService;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class TstDeployment {
    public static JavaArchive createCommonJarDeployment(String exerciseId) {
        return ShrinkWrap
                .create(JavaArchive.class)
                .addAsManifestResource("META-INF/persistence.xml",
                        "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                // test packages
                .addPackages(true, "advancedjpa_" + exerciseId)
                // code packages
                .addPackages(true, "exercise" + exerciseId)
                .addPackages(true, Person.class.getPackage())
                // common packages
                .addPackages(true, JPAProducer.class.getPackage());
    }


}
