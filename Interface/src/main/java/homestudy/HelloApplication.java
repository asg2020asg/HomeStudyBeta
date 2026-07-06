package homestudy;

import homestudy.app.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GerenciadorTelas.setPrimaryStage(stage);
        
        // Define a janela inicial para o modo maximizado
        stage.setMaximized(true);
        // Garante que a janela é redimensionável
        stage.setResizable(true); 
        
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
        
        // A janela principal é mostrada UMA ÚNICA VEZ aqui.
        // Se mudarTela já chama show(), esta linha pode ser redundante ou causar problemas.
        // Com a mudança em GerenciadorTelas, ele não chama show(), então esta linha é necessária.
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}