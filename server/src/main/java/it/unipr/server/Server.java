package it.unipr.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static it.unipr.constants.Constants.*;

/**
 * Class that manages a concurrent server, redirecting connections to threads
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Server {

    private final String ip;
    private final ServerSocket serverSocket;
    private Thread threadAccept;

    /**
     * Open new thread when there are some request and control it on new Session
     *
     * @throws Exception If the ServerSocket fails to be opened
     * @see Session
     * @see ServerSocket
     */
    public Server() throws Exception {
        this.ip = findIp();
        serverSocket = new ServerSocket(LISTEN_PORT);

        this.serverControl();
    }

    /**
     * Method that returns the ip of the current server
     *
     * @return {@code String} containing the ip address of the server
     */
    public String getIp() {
        return this.ip;
    }

    private String findIp() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    private void serverControl() {
        threadAccept = new Thread() {
            public void run() {
                while (PRESENT_CONNECTION < MAX_CONNECTION) {
                    try {
                        Socket socketAccept = serverSocket.accept();
                        PRESENT_CONNECTION++;
                        new Session(socketAccept);
                    } catch (IOException e) { /*to do messaggio di allarme */ }
                }

                try {
                    serverSocket.close();
                } catch (IOException e) { /*to do messaggio di allarme */}

                this.interrupt();
            }
        };
        threadAccept.start();
    }

    /**
     * Close the server
     *
     * @throws Exception If {@code ServerSocket} cannot be closed or the thread interrupted
     * @see ServerSocket
     */
    public void close() throws Exception {
        serverSocket.close();
        threadAccept.interrupt();
    }
}