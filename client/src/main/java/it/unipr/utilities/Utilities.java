package it.unipr.utilities;

import it.unipr.Response.Response;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provides utilities to the module, for example the {@code ShowAlert} method that takes as input title, header and content and
 * produces an alert window
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public final class Utilities {
    private Utilities() {
    }

    /**
     * This method allows loading and setting a scene replacing the active scene
     *
     * @param <T>   class token used to retrieve resources
     * @param stage the active stage
     * @param file  the fxml file to load as next scene
     * @param title the title for the new scene
     * @param c     generic instance of T class
     * @return FXMLLoader object containing the scene
     * @since 1.0
     */
    public static <T> FXMLLoader loadScene(Stage stage, String file, String title, Class<T> c) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(c.getResource(file + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            return fxmlLoader;
        } catch (IOException exception) {
            System.out.println("Can't open view FXML");
        }
        return new FXMLLoader();
    }

    /**
     * This method generates an alert window based on the parameters
     *
     * @param title  used to set the title of the alert window
     * @param header used to set the header of the alert window
     * @param text   used to set the content of the alert window
     * @since 1.0
     */
    public static void showAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * This method generates an alert window based on the parameters that waits for a negative or positive response from the user
     *
     * @param title  Title of the alert window
     * @param header Header of the alert window
     * @param text   Content of the alert window
     * @return true or false based on the response of the user
     */
    public static Boolean showConfirmation(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    /**
     * This method writes a message to the output stream
     *
     * @param output  ObjectOutputStream to write to
     * @param message Message to write
     * @param <T>     Type of the message
     */
    public static <T> void message(ObjectOutputStream output, T message) {
        try {
            output.writeObject(message);  // Request executed correctly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method receives a message from the input stream
     *
     * @param input ObjectInputStream to read from
     * @return Response object
     */
    public static Response<?> receive(ObjectInputStream input) {
        try {
            return (Response<?>) input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
