package entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@NamedEntityGraphs({
@NamedEntityGraph(name = "children",
        attributeNodes = @NamedAttributeNode(value = "children")),
@NamedEntityGraph(name = "children_of_children",
        attributeNodes
        = {
            @NamedAttributeNode(value = "children"),
            @NamedAttributeNode(value = "homeAddress")},
        subgraphs = @NamedSubgraph(
                name = "children2", 
                attributeNodes = @NamedAttributeNode(value = "children")))
})
@Table(name = "PERSONS")
public class Person extends AbstractPerson implements Serializable {

    private static final long serialVersionUID = 1;

    public Person() {
    }

    public Person(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }

    @OneToMany
    private Set<Person> children;

    @OneToOne
    private Address homeAddress;

    public Set<Person> getChildren() {
        return children;
    }

    public void setChildren(Set<Person> children) {
        this.children = children;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

}
