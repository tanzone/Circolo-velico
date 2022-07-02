package it.unipr.controllers.staff;

import it.unipr.connection.Connection;
import it.unipr.transaction.Transaction;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.List;

import static it.unipr.constants.Constants.PROT_GET_ALLTRANS;

/**
 * Class that creates a thread used to check for updates in the transactions database
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.accounts.Member
 * @since 1.0
 */
public class ThreadTransaction implements Runnable {
    private final String ip;
    private final TableView<Transaction> tableTransaction;


    /**
     * Class parametrized constructor
     *
     * @param tableTransaction Tableview with the transactons
     * @param ip               String with server ip address and port
     */
    public ThreadTransaction(TableView<Transaction> tableTransaction, String ip) {
        this.ip = ip;
        this.tableTransaction = tableTransaction;

    }

    /**
     * Method that executes every 1000 ms checking and updating the transactions shown
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String[] serverAddress = ip.split(":");
                    Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                    List<Transaction> transactions = (List<Transaction>) c.doAction(PROT_GET_ALLTRANS, "pizza-acciuga");
                    showPrix(transactions);
                }

                private void showPrix(List<Transaction> transactions) {
                    tableTransaction.getItems().clear();
                    for (Transaction t : transactions)
                        tableTransaction.getItems().add(t);
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
