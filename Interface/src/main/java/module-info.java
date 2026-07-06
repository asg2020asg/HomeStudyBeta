module homestudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens homestudy.app to javafx.fxml;
    opens homestudy.controller to javafx.fxml;
    opens homestudy.view to javafx.fxml;

    exports homestudy.app;
    exports homestudy.controller;
    exports homestudy.view;
    exports homestudy;
}
