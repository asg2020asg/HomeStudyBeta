package homestudy;

import homestudy.app.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GerenciadorTelas.setPrimaryStage(stage);
        
        // Garante que a janela é redimensionável
        stage.setResizable(true); 
        
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
        
        // A janela principal é mostrada UMA ÚNICA VEZ aqui.
        stage.show();
        
        // Define a janela inicial para o modo maximizado APÓS show()
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }
}