package it.unipr.controllers.staff;

import it.unipr.connection.Connection;
import it.unipr.race.Race;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.List;

import static it.unipr.constants.Constants.PROT_GET_ALLRACES;

/**
 * Class that creates a thread used to check for updates in the races database
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.race.Race
 * @since 1.0
 */
public class ThreadPrix implements Runnable {
    private final String ip;
    private final TableView<Race> tableRace;


    /**
     * Class parametrized constructor
     *
     * @param tableRace Table object with races' data
     * @param ip        String with server ip address and port
     */
    public ThreadPrix(TableView<Race> tableRace, String ip) {
        this.ip = ip;
        this.tableRace = tableRace;

    }

    /**
     * Method that executes every 1000 ms checking and updating the race shown
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String[] serverAddress = ip.split(":");
                    Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                    List<Race> races = (List<Race>) c.doAction(PROT_GET_ALLRACES, "pizza-acciuga");
                    showPrix(races);
                }

                private void showPrix(List<Race> races) {
                    tableRace.getItems().clear();
                    for (Race r : races)
                        tableRace.getItems().add(r);
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
