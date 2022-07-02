package it.unipr.controllers.dashboard;

import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.utilities.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classes that manages the interaction with the credit cards objects in the user view
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class CreditController {

    @FXML
    private ImageView imgCredit;
    @FXML
    private Label numCard, firstname, lastname, cvc, date;

    private CreditCard c;
    private MyListener myListener;

    @FXML
    private void click() {
        this.myListener.clickOpenCredit(this.c);
    }

    /**
     * This method is used to set the data of the CreditCard object
     *
     * @param c          {@code CreditCard} that appears when the item is created
     * @param img        {@code Image} element containing the image for the credit card
     * @param myListener {@code MyListiner} object listener
     * @since 1.0
     */
    public void setData(CreditCard c, Image img, MyListener myListener) {
        this.myListener = myListener;
        this.c = c;
        this.imgCredit.setImage(img);
        this.numCard.setText(c.getNumCard());
        this.firstname.setText(c.getFirstname());
        this.lastname.setText(c.getLastname());
        this.cvc.setText(c.getCvc());
        this.date.setText(c.getDate());
    }
}
