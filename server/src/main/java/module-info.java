/**
 * Module that contains all the packages needed to run and manage the server
 */
module it.unipr.server {
    requires java.sql;
    requires common;
    requires AnimateFX;
    requires mysql.connector.java;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens it.unipr.main to javafx.fxml;
    exports it.unipr.main;
    opens it.unipr.controllers.login to javafx.fxml;
    exports it.unipr.controllers.login;
}
