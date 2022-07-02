/**
 * Module containing all common packages needed for server and client
 */
module common {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    exports it.unipr.constants;
    exports it.unipr.accounts.personalinformation;
    exports it.unipr.accounts;
    exports it.unipr.boat;
    exports it.unipr.race;
    exports it.unipr.Response;
    exports it.unipr.transaction;
}