package it.unipr.race;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class that manages all the information concerning a Race
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Race implements Serializable {
    private String id;
    private String name;
    private String date;
    private String winner;
    private String winnerBoat;
    private String price;


    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing all the information to create the new Race
     */
    public Race(HashMap<String, String> values) {
        this.id = values.get("id");
        this.name = values.get("name");
        this.date = values.get("date");
        this.winner = values.get("winner");
        this.winnerBoat = values.get("winnerboat");
        this.price = values.get("price");
    }

    /**
     * Class parametrized constructor
     *
     * @param id         id to be set
     * @param name       name to be set
     * @param date       date to be set
     * @param winner     winner to be set
     * @param winnerBoat winnerBoat to be set
     * @param price      price to be set
     */
    public Race(String id, String name, String date, String winner, String winnerBoat, String price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.winner = winner;
        this.winnerBoat = winnerBoat;
        this.price = price;
    }

    /**
     * Getter of the name attribute
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
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
     * Getter of the price attribute
     *
     * @return price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter of the price attribute
     *
     * @param price price to be set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Getter of the winnnerBoat attribute
     *
     * @return winnerBoat
     */
    public String getWinnerBoat() {
        return winnerBoat;
    }

    /**
     * Setter of the winnerBoat attribute
     *
     * @param winnerBoat winnerBoat to be set
     */
    public void setWinnerBoat(String winnerBoat) {
        this.winnerBoat = winnerBoat;
    }

    /**
     * Getter of the winner attribute
     *
     * @return winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Setter of the winner attribute
     *
     * @param winner winner to be set
     */
    public void setWinner(String winner) {
        this.winner = winner;
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
}
