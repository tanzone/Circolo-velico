package it.unipr.accounts.personalinformation;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The {@code CreditCard} class describes a credit card as an object
 *
 * @author Mamone Maximiliano 308214
 * @author Tanzi  Manuel      307720
 * @version 1.0
 * @since 1.0
 */
public class CreditCard implements Serializable {
    private String numCard;
    private String firstname;
    private String lastname;
    private String date;
    private String cvc;

    /**
     * Class parametrized constructor
     *
     * @param numCard   numCard to be set
     * @param firstname firstname to be set
     * @param lastname  lastname to be set
     * @param date      date to be set
     * @param cvc       cvc to be set
     */
    public CreditCard(String numCard, String firstname, String lastname, String date, String cvc) {
        this.numCard = numCard;
        this.firstname = firstname;
        this.lastname = lastname;
        this.date = date;
        this.cvc = cvc;
    }

    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing all the information to create the new CreditCard
     */
    public CreditCard(HashMap<String, String> values) {
        this.numCard = values.get("number");
        this.firstname = values.get("name");
        this.lastname = values.get("lastname");
        this.date = values.get("date");
        this.cvc = values.get("cvc");
    }

    /**
     * Getter of the numCard attribute
     *
     * @return numCard
     */
    public String getNumCard() {
        return numCard;
    }

    /**
     * Setter of the numCard attribute
     *
     * @param numCard numCard to be set
     */
    public void setNumCard(String numCard) {
        this.numCard = numCard;
    }

    /**
     * Getter of the firstname attribute
     *
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter of the firstname attribute
     *
     * @param firstname firstname to be set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter of the lastname attribute
     *
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter of the lastname attribute
     *
     * @param lastname lastname to be set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter of the date attribute
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter of the date attribute
     *
     * @param date date to be set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter of the cvc attribute
     *
     * @return cvc
     */
    public String getCvc() {
        return cvc;
    }

    /**
     * Setter of the cvc attribute
     *
     * @param cvc cvc to be set
     */
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
