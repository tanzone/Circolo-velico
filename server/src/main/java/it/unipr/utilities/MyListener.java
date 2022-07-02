package it.unipr.utilities;

import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.boat.Boat;

/**
 * This interface defines abstract methods that are defined in different classes,
 * providing different functionalities to different parts of the application
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public interface MyListener {
    /**
     * This abstract method after implementation intercepts the click action of opening a boat
     *
     * @param b the instance of {@code Boat} containing the boat to show
     */
    void clickOpenBoat(Boat b);

    /**
     * This abstract method after implementation manages the click action of adding a new boat
     */
    void clickAddBoat();

    /**
     * This abstract method after implementation manages the click action of adding a new credit card
     */
    void clickAddCredit();

    /**
     * This abstract method after implementation manages the click action of deleting a credit card
     *
     * @param c {@code CreditCard} to delete
     */
    void clickOpenCredit(CreditCard c);
}
