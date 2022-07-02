package it.unipr.controllers.dashboard;

import it.unipr.accounts.personalinformation.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Class that manages the notification graphical element
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.accounts.personalinformation.Notification
 * @since 1.0
 */
public class NotificationController {
    @FXML
    private Label message;

    /**
     * This method is used to set the data of the notification object
     *
     * @param n is the notification that appears when the item is created
     * @since 1.0
     */
    public void setData(Notification n) {
        this.message.setText(n.getMessage());
        this.message.setTextFill(Color.web(n.getColor()));
    }
}
