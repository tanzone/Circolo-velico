package it.unipr.Response;

import java.io.Serializable;
import java.util.List;

import static it.unipr.constants.Constants.MSG_OK;

/**
 * Generic class that is used as an intermediate layer for communication between client and server
 *
 * @param <T> Generic parameter used to define the type of the response
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Response<T> implements Serializable {
    private boolean Empty = true;
    private List<T> response;
    private String message = MSG_OK;

    /**
     * Class only list constructor
     *
     * @param response List of {@code T} class object
     */
    public Response(List<T> response) {
        this.response = response;
    }

    /**
     * Class only message constructor
     *
     * @param message message to be set
     */
    public Response(String message) {
        this.message = message;
    }

    /**
     * Class default constructor
     */
    public Response() {
    }

    /**
     * Getter of the response attribute
     *
     * @return {@code List<T>} class response
     */
    public List<T> getResponse() {
        return response;
    }

    /**
     * Setter of the response attribute
     *
     * @param response response to be set
     */
    public void setResponse(List<T> response) {
        this.response = response;
    }

    /**
     * Getter of the empty attribute
     *
     * @return true if the message is null, false otherwise
     */
    public boolean isEmpty() {
        return Empty;
    }

    /**
     * Setter of the empty attribute
     *
     * @param empty empty value to be set
     */
    public void setEmpty(boolean empty) {
        Empty = empty;
    }

    /**
     * Getter of the message attribute
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter of the message attribute
     *
     * @param message message to be set
     */
    public void setMessage(String message) {
        this.message = message;
        if (Empty) Empty = false;
    }

    /**
     * ToString of the class
     *
     * @return Object as String
     */
    @Override
    public String toString() {
        return "Response{" +
                "response=" + response +
                '}';
    }
}
