/**
 * Module that contains all the packages needed for the client interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
module it.unipr.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires common;
    requires AnimateFX;
    requires javafx.graphics;

    opens it.unipr.main to javafx.fxml;
    exports it.unipr.main;
    opens it.unipr.controllers.login to javafx.fxml;
    exports it.unipr.controllers.login;
    opens it.unipr.controllers.dashboard to javafx.fxml;
    exports it.unipr.controllers.dashboard;
    opens it.unipr.controllers.staff to javafx.fxml;
    exports it.unipr.controllers.staff;

}