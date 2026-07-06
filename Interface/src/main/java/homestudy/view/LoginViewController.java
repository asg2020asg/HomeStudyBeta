package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.controller.UsuarioController;
import homestudy.model.Usuario; // Importar a classe Usuario
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Importar FXMLLoader
import javafx.scene.Parent; // Importar Parent
import javafx.scene.Scene; // Importar Scene
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException; // Importar IOException

public class LoginViewController {
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Label labelMensagem; // Adicionado para exibir mensagens
    private UsuarioController uc = new UsuarioController();

    @FXML
    public void btnEntrar() {
        String email = emailField.getText();
        String senha = senhaField.getText();
        Stage stage = (Stage) emailField.getScene().getWindow(); // Obter o Stage atual

        if (email.isEmpty() || senha.isEmpty()) {
            exibirAlerta("Aviso", "Preencha todos os campos.");
            return;
        }

        Usuario usuarioLogado = uc.Login(email, senha); // Chama o método Login que retorna Usuario ou null

        if (usuarioLogado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/main-view.fxml"));
                Parent root = loader.load();
                MainViewController mainController = loader.getController();
                mainController.setUsuarioLogado(usuarioLogado); // Passa o usuário logado para o MainViewController

                Scene scene;
                if (stage.getWidth() > 0 && stage.getHeight() > 0) {
                    scene = new Scene(root, stage.getWidth(), stage.getHeight());
                } else {
                    scene = new Scene(root);
                }
                stage.setTitle("HomeStudy Beta - " + usuarioLogado.getNome()); // Define o título com o nome do usuário
                stage.setScene(scene);
                stage.show();
                Platform.runLater(() -> stage.setMaximized(true)); // Garante que a tela principal abra maximizada
            } catch (IOException e) {
                exibirAlerta("Erro", "Não foi possível carregar a tela principal: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            exibirAlerta("Erro de Login", "E-mail ou senha incorretos.");
        }
    }

    @FXML
    private void handleForgotPassword() {
        // Lógica para ir para a tela de redefinição de senha
        Stage stage = (Stage) emailField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/forgot-password-view.fxml", "Redefinir Senha");
    }

    @FXML
    private void handleBackButton() {
        // Lógica para voltar para a tela inicial
        Stage stage = (Stage) emailField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // Não precisa de cabeçalho para alertas simples
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
