module homestudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens homestudy to javafx.fxml;
    opens homestudy.controller to javafx.fxml;


    opens homestudy.app to javafx.fxml;

    exports homestudy;
    exports homestudy.controller;
    exports homestudy.app;
}