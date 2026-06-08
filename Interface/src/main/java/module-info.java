module homestudy {
    requires javafx.controls;
    requires javafx.fxml;

    opens homestudy to javafx.fxml;
    opens homestudy.interface_fx to javafx.fxml;

    exports homestudy;
    exports homestudy.interface_fx;
}