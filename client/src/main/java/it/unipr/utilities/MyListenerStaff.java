package it.unipr.utilities;

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
public interface MyListenerStaff {
    /**
     * This abstract method when implemented intercepts the click action for send notification to paid
     *
     * @param b the instance of {@code Boat} containing the boat to show
     */
    void clickSendNot(Boat b);

    /**
     * This abstract method when implemented intercepts the click action for delete boat
     *
     * @param b the instance of {@code Boat} containing the boat to show
     */
    void clickDelBoat(Boat b);

}
