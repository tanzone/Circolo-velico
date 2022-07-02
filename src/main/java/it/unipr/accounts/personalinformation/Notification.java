package it.unipr.accounts.personalinformation;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The {@code Notification} class through its attributes describes the notification that an account could have.
 *
 * @author Mamone Maximiliano 308214
 * @author Tanzi  Manuel      307720
 * @version 1.0
 * @since 1.0
 */
public class Notification implements Serializable {
    private String message;
    private String color;

    /**
     * Class default constructor
     */
    public Notification() {
    }

    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing all the information to create the new Notification
     */
    public Notification(HashMap<String, String> values) {
        this.message = values.get("description");
        this.color = values.get("color");
    }

    /**
     * Class parametrized constructor
     *
     * @param message message to be set
     * @param color   color to be set
     */
    public Notification(String message, String color) {
        this.message = message;
        this.color = color;
    }

    /**
     * Getter of the message attribute
     *
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Setter of the message attribute
     *
     * @param message message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter of the color attribute
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter of the color attribute
     *
     * @param color color to be set
     */
    public void setColor(String color) {
        this.color = color;
    }
}
