package it.unipr.controllers.login;

import animatefx.animation.ZoomIn;
import it.unipr.accounts.Member;
import it.unipr.accounts.Staff;
import it.unipr.connection.Connection;
import it.unipr.controllers.dashboard.DashboardController;
import it.unipr.controllers.staff.StaffController;
import it.unipr.race.Race;
import it.unipr.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static it.unipr.constants.Constants.*;


/**
 * Controller Class of the login scene, also manages the interactions with the connected scenes.
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class SignInUpController implements Initializable {

    @FXML
    private Button signUpButton;
    @FXML
    private RadioButton userTypeButton, staffTypeButton;
    @FXML
    private TextField fiscalCodeField, lastNameField, nameField;
    @FXML
    private PasswordField password2SignUpField, passwordSignUpField;
    @FXML
    private TextField usernameSignUpField, usernameField, ipField;
    @FXML
    private Button signInButton;
    @FXML
    private Label loginError, signUpPassError, signUpUserError;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Pane paneSignIn, paneSignUp;

    private double x, y;

    @FXML
    private void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        new ZoomIn(paneSignIn).play();
        paneSignIn.toFront();
        loginError.setVisible(false);
        signInButton.setDefaultButton(true);
        signUpButton.setDefaultButton(false);
    }

    @FXML //Method that manages the sign up process to the database
    @SuppressWarnings("unchecked")
    private void signUp(ActionEvent actionEvent) {
        if (!fiscalCodeField.getText().isBlank() &&
                !passwordSignUpField.getText().isBlank() &&
                !password2SignUpField.getText().isBlank() &&
                !usernameSignUpField.getText().isBlank() &&
                !nameField.getText().isBlank() &&
                !lastNameField.getText().isBlank() &&
                !ipField.getText().isBlank()
        ) {
            String[] serverAddress = ipField.getText().split(":");
            Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
            //Checks if fiscalCode or username already exist
            List<String> response = (List<String>) c.doAction(PROT_SIGNUP_CHECK, fiscalCodeField.getText() + SEP_PARAM + usernameSignUpField.getText());
            if (Objects.equals(response.get(0), "0")) {
                //Checks if passwords correspond
                if (passwordSignUpField.getText().equals(password2SignUpField.getText())) {
                    //Tries to add the user to the database
                    response = (List<String>) c.doAction(PROT_SIGNUP_USER, nameField.getText() + SEP_PARAM + lastNameField.getText() + SEP_PARAM + fiscalCodeField.getText() + SEP_PARAM + usernameSignUpField.getText() + SEP_PARAM + passwordSignUpField.getText());
                    if (Objects.equals(response.get(0), MSG_OK)) {
                        FXMLLoader loader = Utilities.loadScene((Stage) usernameSignUpField.getScene().getWindow(), "dashboard_screen", "User Menu", DashboardController.class);
                        DashboardController controller = loader.getController();
                        Member member = ((List<Member>) c.doAction(PROT_LOGIN_USER, usernameSignUpField.getText() + SEP_PARAM + passwordSignUpField.getText())).get(0);
                        controller.setData(member, ipField.getText());
                    } else
                        Utilities.showAlert("Error", "Error during signup", "Could not sign you up, try again later");
                } else
                    signUpPassError.setVisible(true);
            } else
                signUpUserError.setVisible(true);
        }
    }

    private void setError(String error) {
        loginError.setText(error);
        loginError.setVisible(true);
    }

    //Method executed everytime someone, staff or user, signs in which randomly chooses a winner for all past races
    @SuppressWarnings("unchecked")
    private void setRaceWinner(Connection connection) {
        List<Race> races = (List<Race>) connection.doAction(PROT_GET_DONERACES, LocalDateTime.now().toString());
        for (Race r : races) {
            List<List<String>> participants = (List<List<String>>) connection.doAction(PROT_GET_ALLSUB, r.getId());
            Random rand = new Random();
            if (participants.size() == 0)
                return;
            List<String> winner = participants.get(rand.nextInt(participants.size()));
            connection.doAction(PROT_SET_WINNER, r.getId() + SEP_PARAM + winner.get(0) + SEP_PARAM + winner.get(1));
        }
    }

    //Method executed everytime someone, staff or user, signs in which sets the status for all the expired boats
    private void setBoatPayment(Connection connection) {
        connection.doAction(PROT_UPDATE_EXPBOAT, "false" + SEP_PARAM + LocalDate.now().toString());
    }

    @FXML //Method that manages the sign in process, Staff and user
    @SuppressWarnings("unchecked")
    private void signIn(ActionEvent actionEvent) {
        //Check the type of login
        if (userTypeButton.isSelected()) {
            //Check if fields are blank
            if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank() && !ipField.getText().isBlank()) {
                String[] serverAddress = ipField.getText().split(":");
                Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                //Check if username and password are correct
                List<Member> response = (List<Member>) c.doAction(PROT_LOGIN_USER, usernameField.getText() + SEP_PARAM + passwordField.getText());
                if (!response.isEmpty() && !response.get(0).equals(MSG_FAIL) && !response.get(0).equals(MSG_FAKE) && !response.get(0).equals(MSG_NULL) && !response.get(0).equals(MSG_NOCONNECTION)) {
                    loginError.setVisible(false);
                    setRaceWinner(c);
                    setBoatPayment(c);
                    FXMLLoader loader = Utilities.loadScene((Stage) usernameField.getScene().getWindow(), "dashboard_screen", "User Menu", DashboardController.class);
                    DashboardController controller = loader.getController();
                    Member member = response.get(0);
                    controller.setData(member, ipField.getText());
                } else if (!response.isEmpty() && response.get(0).equals(MSG_NOCONNECTION))
                    setError(NO_CONNECTION);
                else {
                    setError(WRONG_CREDENTIALS);
                }
            } else
                setError(MISSING_FIELDS);

        } else {
            if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank() && !ipField.getText().isBlank()) {
                String[] serverAddress = ipField.getText().split(":");
                Connection c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));
                List<?> response = c.doAction(PROT_LOGIN_STAFF, usernameField.getText() + SEP_PARAM + passwordField.getText());
                if (!response.isEmpty() && !response.get(0).equals(MSG_FAIL) && !response.get(0).equals(MSG_FAKE) && !response.get(0).equals(MSG_NULL) && !response.get(0).equals(MSG_NOCONNECTION)) {
                    loginError.setVisible(false);
                    setRaceWinner(c);
                    setBoatPayment(c);
                    FXMLLoader loader = Utilities.loadScene((Stage) usernameField.getScene().getWindow(), "staff_screen", "Staff Menu", StaffController.class);
                    StaffController controller = loader.getController();
                    Staff staff = (Staff) response.get(0);
                    controller.setData(staff, ipField.getText());
                } else if (!response.isEmpty() && response.get(0).equals(MSG_NOCONNECTION))
                    setError(NO_CONNECTION);
                else
                    setError(WRONG_CREDENTIALS);
            } else
                setError(MISSING_FIELDS);
        }
    }

    @FXML
    private void changePaneReg(ActionEvent actionEvent) {
        // giocare con l'animazione per trovare la più bella
        new ZoomIn(paneSignUp).play();
        paneSignUp.toFront();
        signUpPassError.setVisible(false);
        signUpUserError.setVisible(false);
        signUpButton.setDefaultButton(true);
        signInButton.setDefaultButton(false);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        makeDragable();
        fillFields();
    }

    private void fillFields() {
        ipField.setText("localhost:4444");

        loginError.setVisible(false);
        signInButton.setDefaultButton(true);
    }

    //Method that creates the transparent effect upon dragging the window
    private void makeDragable() {
        parentPane.setOnMousePressed(((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        parentPane.setOnMouseDragged(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8f);
        }));

        parentPane.setOnDragDone(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);
        }));

        parentPane.setOnMouseReleased(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);
        }));
    }

    @FXML
    private void buttonClicked(MouseEvent mouseEvent) {
        loginError.setVisible(false);
    }
}
