package it.unipr.controllers.dashboard;

import it.unipr.utilities.MyListener;
import javafx.fxml.FXML;


/**
 * Class used to manage the addition of new boat credit card graphical representation
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.accounts.personalinformation.CreditCard
 * @since 1.0
 */
public class AddCreditController {
    private MyListener myListener;

    @FXML
    private void click() {
        this.myListener.clickAddCredit();
    }

    /**
     * Method that sets the listener
     *
     * @param myListener Object of class {@code MyListener}
     */
    public void setData(MyListener myListener) {
        this.myListener = myListener;
    }
}
