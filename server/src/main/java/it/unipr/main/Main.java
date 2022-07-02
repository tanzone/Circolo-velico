package it.unipr.main;

import it.unipr.controllers.login.SignInAdminController;
import it.unipr.utilities.Utilities;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;


/**
 * This class contains the {@code public static void main} of the application
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {
    /**
     * Main method of the application
     *
     * @param args parameters passed as arguments after launching the software
     * @since 1.0
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Utilities.loadScene(stage, "signInAdmin_screen", "SignInAdmin", SignInAdminController.class);

        stage.getIcons().add(new Image(Objects.requireNonNull(SignInAdminController.class.getResourceAsStream("boat.png"))));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}





