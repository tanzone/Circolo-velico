package it.unipr.controllers.dashboard;


import it.unipr.utilities.MyListener;
import javafx.fxml.FXML;


/**
 * Class used to manage the addition of new boat graphical representations
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.boat.Boat
 * @since 1.0
 */
public class AddBoatController {
    private MyListener myListener;

    @FXML
    private void click() {
        this.myListener.clickAddBoat();
    }

    /**
     * Method that sets the listener
     *
     * @param myListener Object of interface {@code MyListener}
     */
    public void setData(MyListener myListener) {
        this.myListener = myListener;
    }
}
