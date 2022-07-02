package it.unipr.controllers.staff;

import it.unipr.accounts.Member;
import it.unipr.accounts.personalinformation.Address;
import it.unipr.connection.Connection;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.List;

import static it.unipr.constants.Constants.PROT_GET_ADDRESS;
import static it.unipr.constants.Constants.PROT_GET_ALLMEMBERS;

/**
 * Class that creates a thread used to check for updates in the member database
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.accounts.Member
 * @since 1.0
 */
public class ThreadMember implements Runnable {
    private final String ip;
    private final TableView<Member> tableMember;

    /**
     * Class parametrized constructor
     *
     * @param tableMember Table object with members' data
     * @param ip          String that contains the ip address of the server
     */
    public ThreadMember(TableView<Member> tableMember, String ip) {
        this.ip = ip;
        this.tableMember = tableMember;
    }

    /**
     * Method that executes every 1000 ms checking and updating the members shown
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String[] serverAddress = ip.split(":");
                    Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                    List<Member> members = (List<Member>) c.doAction(PROT_GET_ALLMEMBERS, "pizza-acciuga");
                    showMembers(members);
                }

                private void showMembers(List<Member> members) {
                    tableMember.getItems().clear();
                    for (Member m : members) {
                        String[] serverAddress = ip.split(":");
                        Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                        List<Address> address = (List<Address>) c.doAction(PROT_GET_ADDRESS, m.getFiscalCode());
                        if (!address.isEmpty())
                            m.setAddress(address.get(0));
                        tableMember.getItems().add(m);
                    }

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
