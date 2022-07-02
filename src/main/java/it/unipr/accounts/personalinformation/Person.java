package it.unipr.accounts.personalinformation;


import java.io.Serializable;

/**
 * The {@code Person} class through its attributes identifies a person
 *
 * @author Mamone Maximiliano 308214
 * @author Tanzi  Manuel      307720
 * @version 1.0
 * @since 1.0
 */
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private Address address;
    private String fiscalCode;

    /**
     * Default Constructor
     */
    public Person() {
    }

    /**
     * Parametrized Constructor
     *
     * @param firstName  {@code String} contains the first name of the person
     * @param lastName   {@code String} contains the last name of the person
     * @param address    {@code Address} contains the address object with the information about where the person lives
     * @param fiscalCode {@code String} contains the fiscalCode of the person
     */
    public Person(String firstName, String lastName, Address address, String fiscalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.fiscalCode = fiscalCode;
    }

    /**
     * Parametrized Constructor
     *
     * @param p {@code Person} contains all the information of a person
     */
    public Person(Person p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.address = p.getAddress();
        this.fiscalCode = p.getFiscalCode();
    }


    /**
     * Get Method of the {@code firstName} attribute
     *
     * @return {@code String} - firstName attribute's value
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set Method of the {@code firstName} attribute
     *
     * @param firstName {@code String} - new value to set to the attribute
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get Method of the {@code lastName} attribute
     *
     * @return {@code String} - lastName attribute's value
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set Method of the {@code lastName} attribute
     *
     * @param lastName {@code String} - new value to set to the attribute
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get Method of the {@code address} attribute
     *
     * @return {@code Address} - address attribute's value
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set Method of the {@code adress} attribute
     *
     * @param address {@code Address} - new value to set to the attribute
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Get Method of the {@code fiscalCode} attribute
     *
     * @return {@code String} - fiscalCode attribute's value
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * Set Method of the {@code fiscalCode} attribute
     *
     * @param fiscalCode {@code String} - new value to set to the attribute
     */
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    /**
     * Default toString method of the class
     *
     * @return {@code String} - contains the value of all the attributes of the object
     */
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", fiscalCode='" + fiscalCode + '\'' +
                '}';
    }
}
