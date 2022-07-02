package it.unipr.controllers.staff;

import it.unipr.boat.Boat;
import it.unipr.connection.Connection;
import it.unipr.utilities.MyListenerStaff;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static it.unipr.constants.Constants.PROT_GET_ALLBOATS;

/**
 * Class that creates a thread used to check for updates in the boat database
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.boat.Boat
 * @since 1.0
 */
public class ThreadBoats implements Runnable {
    private final String ip;
    private final GridPane gridpaneBoats;
    private final List<Image> imgListBoat;
    private final MyListenerStaff myListener;
    private final boolean admin;

    /**
     * Class parametrized constructor
     *
     * @param gridpaneBoats   Grid object to fill with boats graphic elements
     * @param imgListBoat     List of possible images for the boat graphic elements
     * @param ip              String that contains the ip address of the server
     * @param myListenerStaff Listener for the staff controller class
     * @param admin           Boolean that identifies if the account logged is an admin or not
     */
    public ThreadBoats(GridPane gridpaneBoats, List<Image> imgListBoat, String ip, MyListenerStaff myListenerStaff, boolean admin) {
        this.ip = ip;
        this.gridpaneBoats = gridpaneBoats;
        this.imgListBoat = imgListBoat;
        this.myListener = myListenerStaff;
        this.admin = admin;
    }

    /**
     * Method that executes every 1000 ms checking and updating the boats shown
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String[] serverAddress = ip.split(":");
                    Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                    List<Boat> boats = (List<Boat>) c.doAction(PROT_GET_ALLBOATS, "pizza-acciuga");
                    showBoats(boats);
                }

                private void showBoats(List<Boat> boats) {
                    int column = 0, row = 0, minHeight = 500;
                    Random rand = new Random();
                    gridpaneBoats.getChildren().clear();

                    for (Boat b : boats) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(BoatStaffController.class.getResource("boat_screen.fxml"));
                            AnchorPane pane = fxmlLoader.load();
                            BoatStaffController itemController = fxmlLoader.getController();
                            if (b.isPaid())
                                itemController.setData(b, imgListBoat.get(rand.nextInt(imgListBoat.size())), b.getPrice(), "Yes", "#2fb523", b.getOwner(), myListener, admin);
                            else
                                itemController.setData(b, imgListBoat.get(rand.nextInt(imgListBoat.size())), b.getPrice(), "Not yet!", "#ff0000", b.getOwner(), myListener, admin);

                            if (column == 2) {
                                column = 0;
                                row++;
                                minHeight += 300;
                            }
                            gridpaneBoats.add(pane, column++, row);
                            GridPane.setMargin(pane, new Insets(10));
                        } catch (IOException ignored) {
                        }
                    }
                    if (row == 0) minHeight += 300;
                    gridpaneBoats.setMinWidth(775);
                    gridpaneBoats.setMinHeight(minHeight);
                }
            });
            try {
                Thread.sleep(10000);
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
