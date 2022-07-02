package it.unipr.controllers.dashboard;

import it.unipr.boat.Boat;
import it.unipr.utilities.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Class that manages the interaction with the boat Objects
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class BoatController {
    @FXML
    private ImageView imgBoat;
    @FXML
    private Label name, lenght, price, paid;
    private Boat b;
    private MyListener myListener;

    @FXML
    private void click() {
        this.myListener.clickOpenBoat(this.b);
    }


    /**
     * Method that sets the data of the boat graphical elements
     *
     * @param b          {@code Boat} object to be displayed
     * @param img        {@code Image} object containing the image for the boat
     * @param price      {@code double} containing the price to pay for the boat
     * @param paid       {@code String} status of the payment
     * @param color      {@code String} containing the colour of the payment text
     * @param myListener {@code MyListener} object
     */
    public void setData(Boat b, Image img, double price, String paid, String color, MyListener myListener) {
        this.myListener = myListener;
        this.b = b;
        this.imgBoat.setImage(img);
        this.name.setText(b.getName());
        this.lenght.setText(String.valueOf(b.getLength()));
        this.price.setText(String.valueOf(price));
        this.paid.setTextFill(Color.web(color));
        this.paid.setText(paid);
    }
}
