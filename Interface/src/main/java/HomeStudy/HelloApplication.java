package homestudy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Aponta exatamente para o seu arquivo FXML de login
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/homestudy/tela-login.fxml"));

        // Cria a cena. O tamanho se adapta ao prefWidth/prefHeight do FXML ou você define aqui
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Sistema de Login");
        stage.setScene(scene);
        stage.setResizable(false); // Impede o usuário de maximizar e quebrar o layout simples
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}