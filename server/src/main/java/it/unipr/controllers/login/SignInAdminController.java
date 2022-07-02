package it.unipr.controllers.login;

import animatefx.animation.ZoomIn;
import it.unipr.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
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
public class SignInAdminController implements Initializable {

    @FXML
    private Label ipAddress, nPort, error;
    @FXML
    private Pane paneServer, paneLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane parentPane;

    private double x, y;
    private Server server;


    @FXML
    private void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void start(ActionEvent actionEvent) {
        String user = this.username.getText();
        String pass = this.password.getText();

        Integer check = checkAdmin(user, pass);

        if (check == 1) {
            try {
                this.server = new Server();
                this.ipAddress.setText(this.server.getIp());
                this.nPort.setText(String.valueOf(LISTEN_PORT));
            } catch (Exception ignored) {
            }

            new ZoomIn(paneServer).play();
            paneServer.toFront();
        } else if (check != -1) {
            this.error.setText("Admin doesn't exists!");
            this.error.setVisible(true);
            this.password.setText("");
        }
    }

    @FXML
    private void closeServer(ActionEvent actionEvent) {
        new ZoomIn(paneLogin).play();
        paneServer.toBack();
        try {
            this.server.close();
        } catch (Exception ignored) {
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.error.setVisible(false);
        makeDragable();
    }


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


    private int checkAdmin(String user, String pass) {
        String query = ("SELECT COUNT(*) FROM staff " +
                "WHERE username='%s' and password='%s' and admin='1'").formatted(user, pass);

        Connection conn = null;
        Statement stat = null;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(SQL_SERVER + "?user=" + SQL_USER + "&password=" + SQL_PASS);
            stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(query);
            while (resultSet.next())
                result = resultSet.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            this.error.setText("Can't connect to server!");
            this.error.setVisible(true);
            this.password.setText("");
            return -1;
        } finally {
            try {
                Objects.requireNonNull(stat).close();
            } catch (SQLException ignored) {
            } catch (NullPointerException e) {
                this.error.setText("Can't connect to server!");
                this.error.setVisible(true);
                this.password.setText("");
                return -1;
            }
            try {
                Objects.requireNonNull(conn).close();
            } catch (SQLException ignored) {
            } catch (NullPointerException e) {
                this.error.setText("Can't connect to server!");
                this.error.setVisible(true);
                this.password.setText("");
                return -1;
            }
            return result;
        }
    }
}
