package homestudy;

import homestudy.app.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GerenciadorTelas.setPrimaryStage(stage);
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
    }

    public static void main(String[] args) {
        launch();
    }
}
