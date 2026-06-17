package homestudy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // AJUSTE AQUI: Adicionado "/homestudy/" antes do nome do arquivo
        // Mude de hello-view.fxml para tela-principal.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/homestudy/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}