package entities;

import javax.persistence.Entity;

@Entity
public class Address {
    private String street;
    private String number;
    private String zipCode;
    private String city;
    private String country;
}
