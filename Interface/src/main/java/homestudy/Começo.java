package homestudy;

import homestudy.app.GerenciadorTelas;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Começo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GerenciadorTelas.setPrimaryStage(stage);
        

        stage.setResizable(true); 
        
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/inicio-view.fxml", "HomeStudy Beta");
        

        stage.show();
        

        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }
}