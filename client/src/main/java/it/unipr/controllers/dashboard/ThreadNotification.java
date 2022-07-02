package it.unipr.controllers.dashboard;

import it.unipr.accounts.personalinformation.Notification;
import it.unipr.connection.Connection;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;

import static it.unipr.constants.Constants.PROT_GET_NOTIFICATION;

/**
 * Class that creates a thread used to check for incoming notifications
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class ThreadNotification implements Runnable {
    private final String ip;
    private ImageView imgBell;
    private String username;

    /**
     * Class parametrized constructor
     *
     * @param ip       String containing the ip of the server to query
     * @param imgBell  Image item containing the notification bell
     * @param username Username of the user logged in
     */
    public ThreadNotification(String ip, ImageView imgBell, String username) {
        this.ip = ip;
        this.imgBell = imgBell;
        this.username = username;
    }


    /**
     * Method that executes every 5000 ms checking and updating the notifications shown
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                @SuppressWarnings("unchecked")
                public void run() {
                    String[] serverAddress = ip.split(":");
                    Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                    List<Notification> notifications = (List<Notification>) c.doAction(PROT_GET_NOTIFICATION, username);
                    showNotification(notifications);
                }

                private void showNotification(List<Notification> notifications) {
                    if (!notifications.isEmpty()) {
                        Image image = new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("notification.png")));
                        imgBell.setImage(image);
                    } else {
                        Image image = new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("bell.png")));
                        imgBell.setImage(image);
                    }
                }
            });
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
