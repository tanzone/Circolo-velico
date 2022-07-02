package it.unipr.accounts;

import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.Person;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class that manages all the information concerning a staff, extends {@code Person}
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see Person
 * @since 1.0
 */
public class Staff extends Person implements Serializable {
    private String username;
    private String password;
    private Boolean admin;

    /**
     * Class default constructor
     */
    public Staff() {
    }

    /**
     * Class copy constructor
     *
     * @param s {@code Staff} object to be copied
     */
    public Staff(Staff s) {
        super(s.getFirstName(), s.getLastName(), s.getAddress(), s.getFiscalCode());
        this.username = s.getUsername();
        this.password = s.getPassword();
        this.admin = s.isAdmin();
    }

    /**
     * Class specific constructor to create object from map
     *
     * @param values {@code HashMap<String, String>} containing all information about the staff to create
     */
    public Staff(HashMap<String, String> values) {
        super(values.get("firstname"), values.get("lastname"), new Address(), values.get("fiscalCode"));
        this.username = values.get("username");
        this.password = values.get("password");
        this.admin = values.get("admin").equals("1");
    }

    /**
     * Class simple constructor with just username and password
     *
     * @param username username to be set
     * @param password password to be set
     */
    public Staff(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Class parametrized constructor
     *
     * @param firstName  firstname to be set
     * @param lastName   lastname to be set
     * @param address    address to be set
     * @param fiscalCode fiscalCode to be set
     * @param username   username to be set
     * @param password   password to be set
     */
    public Staff(String firstName, String lastName, Address address, String fiscalCode, String username, String password) {
        super(firstName, lastName, address, fiscalCode);
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of the username attribute
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username attribute
     *
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the password attribute
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password attribute
     *
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of the admin attribute
     *
     * @return true if admin, false otherwise
     */
    public Boolean isAdmin() {
        return admin;
    }

    /**
     * Setter of the admin attribute
     *
     * @param admin admin to be set
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * To string of the class
     *
     * @return String of the object
     */
    @Override
    public String toString() {
        return "Staff{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
