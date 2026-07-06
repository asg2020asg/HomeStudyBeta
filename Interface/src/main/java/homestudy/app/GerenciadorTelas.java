package homestudy.app;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    /**
     * Muda a cena (tela) em um determinado Stage e retorna o controlador da nova tela.
     *
     * @param stage O Stage (janela) onde a cena será mudada.
     * @param fxmlPath O caminho para o arquivo FXML da nova tela.
     * @param title O título da janela.
     * @return O controlador da nova tela carregada.
     */
    public static Object mudarTela(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GerenciadorTelas.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene;
            if (stage.getScene() != null && stage.getWidth() > 0 && stage.getHeight() > 0) {
                scene = new Scene(root, stage.getWidth(), stage.getHeight());
            } else {
                scene = new Scene(root);
            }
            stage.setTitle(title);
            stage.setScene(scene);
            
            // Garante que a tela permaneça maximizada executando após o ciclo de layout
            Platform.runLater(() -> stage.setMaximized(true));
            // stage.setResizable(true) também é definido uma vez no HelloApplication.
            
            // stage.show() não é chamado aqui, pois o Stage já deve estar visível.
            // Apenas a cena é trocada.

            return fxmlLoader.getController(); // Retorna o controlador da tela carregada
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
            exibirAlerta("Erro de Carregamento", "Não foi possível carregar a tela: " + title + "\nDetalhes: " + e.getMessage());
            return null;
        }
    }

    // Novo método para exibir alertas
    public static void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}