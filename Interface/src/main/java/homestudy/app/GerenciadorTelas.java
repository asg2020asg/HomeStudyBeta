package homestudy.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Importar Alert
import javafx.scene.control.Alert.AlertType; // Importar AlertType
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciadorTelas {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        GerenciadorTelas.primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void mudarTela(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GerenciadorTelas.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
            exibirAlerta("Erro de Carregamento", "Não foi possível carregar a tela: " + title + "\nDetalhes: " + e.getMessage());
        }
    }

    // Novo método para exibir alertas
    public static void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR); // Pode ser ajustado para outros tipos de alerta
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
