package it.unipr.connection;

import it.unipr.Response.Response;
import it.unipr.utilities.Utilities;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.unipr.constants.Constants.*;


/**
 * Class used to establish and manage the connection to the server
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Connection {

    private final String ip;
    private final String ipServer;
    private final int portServer;
    private String protocol;
    private String param;

    /**
     * Parametrized constructor
     *
     * @param ipServer   {@code String} contains the ip of the wanted server
     * @param portServer {@code int} contains the port number of the wanted server
     */
    public Connection(String ipServer, int portServer) {
        this.ip = getIp();
        this.ipServer = ipServer;
        this.portServer = portServer;
    }

    /**
     * Get method of the {@code ip} attribute
     *
     * @return {@code String} - ip attribute's value
     */
    private String getIp() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }


    /**
     * Method that manages the connection to the server, waiting for a response and acting accordingly
     *
     * @param protocol {@code String} contains the protocol used to send a message
     * @param param    {@code String} contains additional parameters for the request
     * @return {@code String} - calls the {@code readData} with result as parameter, requesting the analysis of the data
     */
    public List<?> doAction(String protocol, String param) {
        this.protocol = protocol;
        this.param = param;
        try {
            Socket socket = new Socket(this.ipServer, this.portServer);

            OutputStream output = socket.getOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            InputStream input = socket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);

            Utilities.message(objectOutput, protocol + ";" + param);

            Response<?> response = Utilities.receive(objectInput);
            this.close(socket, objectInput, objectOutput);

            if (response == null)
                return Collections.singletonList(MSG_FAIL);

            return this.readData(response);
        } catch (ConnectException c) {
            return Collections.singletonList(MSG_NOCONNECTION);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(MSG_FAIL);
        }
    }

    private void close(Socket s, ObjectInputStream input, ObjectOutputStream outuput) {
        try {
            s.close();
            input.close();
            outuput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method that takes the result of the request as input and verifies if the request failed or not, and if not, send the result to be parsed
    private List<?> readData(Response<?> response) {
        return switch ((String) response.getMessage()) {
            case MSG_FAIL -> Collections.singletonList(MSG_FAIL);
            case MSG_FAKE -> Collections.singletonList(MSG_FAKE);
            default -> splitDataForProtocol(response);
        };
    }

    //Method that parses the result according to the type of protocol used
    private List<?> splitDataForProtocol(Response<?> response) {

        if (this.protocol.equals(PROT_LOGIN_USER)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_LOGIN_STAFF)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_BOAT_LOAD)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_NOTSUBRACE_LOAD)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_SUBRACE_LOAD)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ADDRESS)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ALLBOATS)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ALLMEMBERS)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ALLRACES)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ALLTRANS)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_CARD)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_NOTIFICATION)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_EXPBMEMBER)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_ALLSUB)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_GET_DONERACES)) {
            return response.getResponse();
        }
        if (this.protocol.equals(PROT_SET_WINNER)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_UPDATE_EXPBOAT)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_MEMBER)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_SIGNUP_CHECK)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_SIGNUP_USER)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_NOTIFICATION)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_UPDATE_MEMBER)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_ADD_CARD)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_CARD)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_ADD_TRANSACTION)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_ADD_NOTIFICATION)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_BOAT)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_ADD_BOAT)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_UPDATE_BOAT)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_ADD_RACE)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_SUBSCRIBE_RACE)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_SUBSCRIPTION)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_GET_SUBSCRIBEDBOAT)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_SET_ADDRESS)) {
            return Collections.singletonList(response.getMessage());
        }
        if (this.protocol.equals(PROT_DELETE_RACE)) {
            return Collections.singletonList(response.getMessage());
        }


        return new ArrayList<>();
    }
}
