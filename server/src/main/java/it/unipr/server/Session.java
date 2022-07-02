package it.unipr.server;

import it.unipr.Response.Response;
import it.unipr.accounts.Member;
import it.unipr.accounts.Staff;
import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.accounts.personalinformation.Notification;
import it.unipr.boat.Boat;
import it.unipr.race.Race;
import it.unipr.transaction.Transaction;
import it.unipr.utilities.Utilities;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static it.unipr.constants.Constants.*;


/**
 * Class the manages a single active session, does this through an independent thred for each connection
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Session implements Runnable {
    private final Socket communicationSocket;
    private final Thread openedThread;


    /**
     * Open thread for this session with all details of server
     *
     * @param sock Socket that identify the communication with client
     */
    public Session(Socket sock) {
        this.communicationSocket = sock;
        this.openedThread = new Thread(this, "Thread N." + PRESENT_CONNECTION);
        this.openedThread.start();
        NUM_REQUEST++;
    }

    /**
     * Function for the thread that control the session
     */
    public void run() {
        try {
            OutputStream output = this.communicationSocket.getOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            InputStream input = this.communicationSocket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            this.manageRequest(objectInput, objectOutput);
            this.closeConnection(objectInput, objectOutput);
        } catch (IOException ignored) {
        }
    }

    private void manageRequest(ObjectInputStream input, ObjectOutputStream output) {
        String request = MSG_NULL;

        try {
            request = (String) input.readObject();
        } catch (Exception ignored) {
        }

        filterRequest(request, output);
    }

    /**
     * Filter the request this a protocol and send all.
     *
     * @param request string that contain the request written with a standard protocol
     */
    private void filterRequest(String request, ObjectOutputStream output) {
        String[] msg = request.split(";");
        String query;
        List<String> params = Arrays.asList(msg[1].split(SEP_PARAM));
        Response<?> response = new Response<>();
        Integer result, result_2;

        switch (msg[0]) {
            case PROT_LOGIN_USER:
                query = ("SELECT * FROM user s JOIN person p ON s.personId = p.id WHERE s.username='%s' and s.password='%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, MEMBER_PARAMS, "");
                break;
            case PROT_LOGIN_STAFF:
                query = ("SELECT * FROM staff s JOIN person p ON s.personId = p.id WHERE s.username='%s' and s.password='%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, STAFF_PARAMS, "");
                break;
            case PROT_BOAT_LOAD:
                query = ("SELECT * FROM boat b JOIN user u ON b.username = u.username WHERE b.username='%s'").formatted(params.get(0));
                response = this.connectDatabase(query, BOATS_PARAMS, "");
                break;
            case PROT_ADD_BOAT:
                if (params.size() == 5)
                    query = ("INSERT INTO boat VALUES (NULL, '%s', '%s', '%s', '%s', '%s', NULL)").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4).equals("true") ? "1" : "0");
                else
                    query = ("INSERT INTO boat VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4).equals("true") ? "1" : "0", params.get(5));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_DELETE_BOAT:
                query = ("DELETE FROM `boat` WHERE `boat`.`ID` = '%s'").formatted(params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_NOTSUBRACE_LOAD:
                query = ("SELECT r.id, r.name, r.date, r.winner, r.winnerBoat, r.price FROM race r WHERE r.ID NOT IN\n" +
                        "(SELECT r1.ID \n" +
                        "FROM race r1 LEFT JOIN user_race u1 ON r1.ID = u1.raceID\n" +
                        "GROUP BY r1.ID, u1.username HAVING u1.username = '%s') AND r.date > '%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, RACE_PARAMS, "");
                break;
            case PROT_SUBRACE_LOAD:
                query = ("SELECT r.ID, r.name, r.date, r.winner, r.winnerBoat, r.price \n" +
                        "FROM race r LEFT JOIN user_race u ON r.ID = u.raceID\n" +
                        "GROUP BY r.ID, r.name, r.date, r.winner, r.winnerBoat, r.price, u.username HAVING u.username = '%s' AND r.date > '%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, RACE_PARAMS, "");
                break;
            case PROT_SIGNUP_CHECK:
                query = ("SELECT COUNT(*) as Count FROM person p JOIN user u ON u.personid = p.id WHERE p.fiscalcode='%s' or u.username='%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, null, "Count");
                break;
            case PROT_SIGNUP_USER:
                query = ("INSERT INTO person VALUES (NULL, '%s', '%s', '%s', NULL)").formatted(params.get(0), params.get(1), params.get(2));
                result = this.updateDatabase(query);
                if (result == 1) {
                    query = ("INSERT INTO user VALUES ('%s', '%s', '2001-01-01', '%s')").formatted(params.get(3), params.get(4), this.getID("person", "id"));
                    result_2 = this.updateDatabase(query);
                    response.setMessage((result_2 == 1) ? MSG_OK : MSG_FAIL);
                } else {
                    response.setMessage(MSG_FAIL);
                }
                break;
            case PROT_SUBSCRIBE_RACE:
                query = ("INSERT INTO user_race VALUES ('%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_DELETE_SUBSCRIPTION:
                query = ("DELETE FROM `user_race` WHERE `user_race`.`raceID` = '%s' AND `user_race`.`username` = '%s' AND `user_race`.`boatID` = '%s'").formatted(params.get(0), params.get(1), params.get(2));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_GET_SUBSCRIBEDBOAT:
                query = ("SELECT u.boatID FROM user_race u WHERE u.raceID = '%s' AND u.username = '%s'").formatted(params.get(0), params.get(1));
                response = this.connectDatabase(query, null, "boatID");
                break;
            case PROT_GET_ADDRESS:
                query = ("SELECT * FROM address a JOIN person p ON a.id = p.addressid WHERE p.fiscalCode = '%s'").formatted(params.get(0));
                response = this.connectDatabase(query, ADDRESS_PARAMS, "");
                break;
            case PROT_SET_ADDRESS:
                query = ("DELETE FROM `address` WHERE `address`.`ID` IN (SELECT `person`.`addressId` FROM `person` WHERE `person`.`fiscalCode` = '%s')").formatted(params.get(5));
                result = this.updateDatabase(query);
                query = ("INSERT INTO address VALUES (NULL, '%s', '%s', '%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
                result = this.updateDatabase(query);
                if (result == 1) {
                    query = ("UPDATE person p SET p.addressId = '%s' WHERE p.fiscalCode = '%s'").formatted(this.getID("address", "id"), params.get(5));
                    result_2 = this.updateDatabase(query);
                    response.setMessage((result_2 == 1) ? MSG_OK : MSG_FAIL);
                } else
                    response.setMessage(MSG_FAIL);
                break;
            case PROT_GET_ALLBOATS:
                query = ("SELECT * FROM user u JOIN boat b ON u.username=b.username");
                response = this.connectDatabase(query, BOATS_PARAMS, "");
                break;
            case PROT_GET_ALLMEMBERS:
                query = ("SELECT * FROM user s JOIN person p ON s.personId = p.id");
                response = this.connectDatabase(query, MEMBER_PARAMS, "");
                break;
            case PROT_GET_EXPBMEMBER:
                query = ("SELECT * FROM user u JOIN boat b ON b.username = u.username JOIN person p ON u.personId = p.id WHERE b.paid = '0'");
                response = this.connectDatabase(query, MEMBER_PARAMS, "");
                break;
            case PROT_GET_ALLRACES:
                query = ("SELECT * FROM race");
                response = this.connectDatabase(query, RACE_PARAMS, "");
                break;
            case PROT_GET_ALLTRANS:
                query = ("SELECT * FROM transaction");
                response = this.connectDatabase(query, TRANS_PARAMS, "");
                break;
            case PROT_UPDATE_BOAT:
                query = ("UPDATE boat b SET b.paid = '%s' WHERE b.id = '%s'").formatted(params.get(0).equals("true") ? "1" : "0", params.get(1));
                result = this.updateDatabase(query);
                query = ("UPDATE boat b SET b.expireDate = '%s' WHERE b.id = '%s'").formatted(params.get(2), params.get(1));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_UPDATE_EXPBOAT:
                query = ("UPDATE boat b SET b.paid = '%s' WHERE b.expireDate <= '%s' AND b.paid = '1'").formatted(params.get(0).equals("true") ? "1" : "0", params.get(1));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_GET_CARD:
                query = ("SELECT * FROM creditcard c JOIN user_card u ON c.number = u.number WHERE u.username = '%s'").formatted(params.get(0));
                response = this.connectDatabase(query, CREDIT_PARAMS, "");
                break;
            case PROT_ADD_CARD:
                query = ("INSERT INTO creditcard VALUES ('%s', '%s', '%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
                result = this.updateDatabase(query);
                if (result == 1) {
                    query = ("INSERT INTO user_card VALUES ('%s', '%s')").formatted(params.get(5), params.get(0));
                    result_2 = this.updateDatabase(query);
                    response.setMessage((result_2 == 1) ? MSG_OK : MSG_FAIL);
                } else
                    response.setMessage(MSG_FAIL);
                break;
            case PROT_DELETE_CARD:
                query = ("DELETE FROM `creditcard` WHERE `creditcard`.`number` = '%s'").formatted(params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_ADD_RACE:
                query = ("INSERT INTO race VALUES (NULL, '%s', '%s', '%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_DELETE_RACE:
                query = ("DELETE FROM `race` WHERE `race`.`ID` = '%s'").formatted(params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_ADD_TRANSACTION:
                query = ("INSERT INTO transaction VALUES (NULL, '%s', '%s', '%s', '%s', '%s')").formatted(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_ADD_NOTIFICATION:
                query = ("INSERT INTO notification VALUES (NULL, '%s', '%s')").formatted(params.get(0), params.get(1));
                result = this.updateDatabase(query);
                if (result == 1) {
                    query = ("INSERT INTO user_notification VALUES ('%s', '%s')").formatted(this.getID("notification", "id"), params.get(2));
                    result_2 = this.updateDatabase(query);
                    response.setMessage((result_2 == 1) ? MSG_OK : MSG_FAIL);
                } else
                    response.setMessage(MSG_FAIL);
                break;
            case PROT_GET_NOTIFICATION:
                query = ("SELECT n.description, n.color FROM notification n JOIN user_notification u ON n.id = u.notificationID WHERE u.username = '%s'").formatted(params.get(0));
                response = this.connectDatabase(query, NOTIFICATION_PARAMS, "");
                break;
            case PROT_DELETE_NOTIFICATION:
                query = ("DELETE FROM `notification` WHERE `notification`.`ID` IN (SELECT `notification`.`ID` FROM `notification` JOIN `user_notification` ON `notification`.`ID` = `user_notification`.`notificationID` WHERE `user_notification`.`username` = '%s')").formatted(params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_UPDATE_MEMBER:
                query = ("UPDATE user m SET m.seasonPass = '%s' WHERE m.username = '%s'").formatted(params.get(0), params.get(1));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_GET_DONERACES:
                query = ("SELECT * FROM race r WHERE r.date <= '%s' AND r.winner = 'TBD'").formatted(params.get(0));
                response = this.connectDatabase(query, RACE_PARAMS, "");
                break;
            case PROT_GET_ALLSUB:
                query = ("SELECT u.username, u.boatID FROM race r JOIN user_race u ON r.id = u.raceID WHERE r.id = '%s'").formatted(params.get(0));
                response = this.mixedResponse(query, new String[]{"username", "boatID"});
                break;
            case PROT_SET_WINNER:
                query = ("UPDATE race r SET r.winner = '%s' WHERE r.id = '%s'").formatted(params.get(1), params.get(0));
                result = this.updateDatabase(query);
                query = ("UPDATE race r SET r.winnerBoat = '%s' WHERE r.id = '%s'").formatted(params.get(2), params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;
            case PROT_DELETE_MEMBER:
                query = ("DELETE FROM `user` WHERE `user`.`username` = '%s'").formatted(params.get(0));
                result = this.updateDatabase(query);
                response.setMessage((result == 1) ? MSG_OK : MSG_FAIL);
                break;

            case MSG_NULL:
                break;

            default:
                Utilities.message(output, new Response<>(MSG_FAKE));
                return;
        }

        if (response.isEmpty()) Utilities.message(output, new Response<>(MSG_FAIL));
        else Utilities.message(output, response);

    }

    private String getID(String table, String id) {
        String query = "SELECT MAX(" + table + "." + id + ") as " + id + " FROM " + table;
        return this.connectDatabase(query, null, id).getMessage();

    }

    private Response<List<String>> mixedResponse(String query, String[] params) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(SQL_SERVER, SQL_USER, SQL_PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = this.executeQuery(stm, query);

            List<List<String>> list = new ArrayList<>();
            List<String> value = new ArrayList<>();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    value = new ArrayList<>();
                    for (String param : params)
                        value.add(rs.getString(param));
                    list.add(value);
                }
            }
            Response<List<String>> response = new Response<>(list);
            response.setEmpty(false);

            conn.close();

            return response;
        } catch (Exception ignored) {
        }
        return null;
    }

    private Response<?> createResponse(ResultSet rs, String[] params) {
        HashMap<String, String> values = new HashMap<>();
        try {
            switch (params[0]) {
                case "Boat" -> {
                    List<Boat> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Boat boat = new Boat(values);
                            list.add(boat);
                        }
                    }
                    Response<Boat> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Address" -> {
                    List<Address> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Address address = new Address(values);
                            list.add(address);
                        }
                    }
                    Response<Address> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Race" -> {
                    List<Race> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Race race = new Race(values);
                            list.add(race);
                        }
                    }
                    Response<Race> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Member" -> {
                    List<Member> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Member member = new Member(values);
                            list.add(member);
                        }
                    }
                    Response<Member> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Staff" -> {
                    List<Staff> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Staff staff = new Staff(values);
                            list.add(staff);
                        }
                    }
                    Response<Staff> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Transaction" -> {
                    List<Transaction> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Transaction trans = new Transaction(values);
                            list.add(trans);
                        }
                    }
                    Response<Transaction> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "CreditCard" -> {
                    List<CreditCard> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            CreditCard card = new CreditCard(values);
                            list.add(card);
                        }
                    }
                    Response<CreditCard> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                case "Notification" -> {
                    List<Notification> list = new ArrayList<>();
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            for (int i = 1; i < params.length; i++)
                                values.put(params[i], rs.getString(params[i]));
                            Notification not = new Notification(values);
                            list.add(not);
                        }
                    }
                    Response<Notification> response = new Response<>(list);
                    response.setEmpty(false);
                    return response;
                }
                default -> throw new Exception("Something Happened");
            }
        } catch (Exception e) {
        }
        return null;
    }

    //Create the connection to sql server
    private Response<?> connectDatabase(String query, String[] params, String column) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(SQL_SERVER, SQL_USER, SQL_PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = this.executeQuery(stm, query);
            Response<?> response;
            if (params != null)
                response = createResponse(rs, params);
            else
                response = easyResponse(rs, column);
            conn.close();

            return response;
        } catch (Exception ignored) {
        }
        return null;
    }

    private Response<String> easyResponse(ResultSet rs, String column) {
        Response<String> response = new Response<>();
        try {
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    response = new Response<>();
                    response.setMessage(rs.getString(column));
                }
            }
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    private Integer updateDatabase(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(SQL_SERVER, SQL_USER, SQL_PASS);
            Statement stm = conn.createStatement();
            Integer result = stm.executeUpdate(query);
            conn.close();
            return result;
        } catch (Exception ignored) {
        }
        return null;
    }

    //Execute the query
    private ResultSet executeQuery(Statement stm, String query) throws SQLException {
        return stm.executeQuery(query);
    }

    //Close all opened connection
    private void closeConnection(ObjectInputStream input, ObjectOutputStream output) {
        PRESENT_CONNECTION--;
        try {
            input.close();
            communicationSocket.close();
        } catch (IOException e) {
            return;
        }

        try {
            output.close();
        } catch (IOException e) {
            return;
        }
        this.openedThread.interrupt();
    }
}