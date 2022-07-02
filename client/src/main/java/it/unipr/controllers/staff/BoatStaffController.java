package it.unipr.controllers.staff;

import it.unipr.boat.Boat;
import it.unipr.utilities.MyListenerStaff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Class that manages the graphical visualization and interaction with the boat elements in the staff view
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.boat.Boat
 * @since 1.0
 */
public class BoatStaffController {

    @FXML
    private Button btnDelete, btnNotification;
    @FXML
    private ImageView imgBoat;
    @FXML
    private Label paid, price, length, name, username;

    private Boat boat;
    private MyListenerStaff myListener;

    /**
     * Method that sets all the data contained in the visual boat block
     *
     * @param b          Boat object to be represented
     * @param img        Image object used to represent the boat
     * @param price      Double price of the boat
     * @param paid       String payment status
     * @param color      Color of the text based on the status
     * @param user       Owner of the boat
     * @param myListener Listener
     * @param admin      Boolean that enables/disables certain features based on the type of staff
     */
    public void setData(Boat b, Image img, double price, String paid, String color, String user, MyListenerStaff myListener, boolean admin) {
        this.boat = b;
        this.myListener = myListener;
        this.imgBoat.setImage(img);
        this.name.setText(b.getName());
        this.length.setText(String.valueOf(b.getLength()));
        this.price.setText(String.valueOf(price));
        this.paid.setTextFill(Color.web(color));
        this.paid.setText(paid);
        this.username.setText(user);
        this.btnDelete.setDisable(!admin);
        if (paid.equals("Yes")) this.btnNotification.setDisable(true);
    }

    @FXML
    private void notification(ActionEvent actionEvent) {
        this.myListener.clickSendNot(this.boat);
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
        this.myListener.clickDelBoat(this.boat);
    }
}
