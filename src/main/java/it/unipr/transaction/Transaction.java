package it.unipr.transaction;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class that manages all the information concerning a transaction
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Transaction implements Serializable {
    private String id;
    private String motive;
    private double total;
    private String username;
    private String method;
    private String date;


    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing all the information to create the new Transaction
     */
    public Transaction(HashMap<String, String> values) {
        this.id = values.get("id");
        this.motive = values.get("motive");
        this.total = Double.parseDouble(values.get("total"));
        this.username = values.get("username");
        this.method = values.get("method");
        this.date = values.get("date");
    }

    /**
     * Class parametrized constructor
     *
     * @param motive   motive to be set
     * @param total    total to be set
     * @param username username to be set
     * @param method   method to be set
     * @param date     date to be set
     */
    public Transaction(String motive, double total, String username, String method, String date) {
        this.motive = motive;
        this.total = total;
        this.username = username;
        this.method = method;
        this.date = date;
    }

    /**
     * Getter of the id attribute
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of the id attribute
     *
     * @param id id to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of the motive attribute
     *
     * @return motive
     */
    public String getMotive() {
        return motive;
    }

    /**
     * Setter of the motive attribute
     *
     * @param motive motive to be set
     */
    public void setMotive(String motive) {
        this.motive = motive;
    }

    /**
     * Getter of the total attribute
     *
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter of the total attribute
     *
     * @param total total to be set
     */
    public void setTotal(double total) {
        this.total = total;
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
     * Getter of the method attribute
     *
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Setter of the method attribute
     *
     * @param method method to be set
     */
    public void setMethod(String method) {
        this.method = method;
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
}
