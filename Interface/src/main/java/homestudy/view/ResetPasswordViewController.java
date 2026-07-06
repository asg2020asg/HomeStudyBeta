package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.controller.UsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ResetPasswordViewController {

    @FXML private PasswordField novaSenhaField;
    @FXML private PasswordField repetirSenhaField;
    @FXML private Label labelMensagem;

    private UsuarioController usuarioController = new UsuarioController();

    // Campo estático para receber o email do usuário da tela anterior
    private static String userEmailToReset;

    public static void setUserEmailToReset(String email) {
        userEmailToReset = email;
    }

    @FXML
    private void handleResetPassword() {
        String novaSenha = novaSenhaField.getText();
        String repetirSenha = repetirSenhaField.getText();

        if (novaSenha.isEmpty() || repetirSenha.isEmpty()) {
            GerenciadorTelas.exibirAlerta("Aviso", "Por favor, preencha todos os campos de senha.");
            return;
        }

        if (!novaSenha.equals(repetirSenha)) {
            GerenciadorTelas.exibirAlerta("Erro", "As senhas não coincidem. Por favor, digite novamente.");
            return;
        }

        if (userEmailToReset == null || userEmailToReset.isEmpty()) {
            GerenciadorTelas.exibirAlerta("Erro", "Não foi possível identificar o usuário para redefinir a senha. Por favor, tente novamente desde o início.");
            // Redireciona para a tela de login
            Stage stage = (Stage) novaSenhaField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
            return;
        }

        // Chama o método do controlador para atualizar a senha
        boolean sucesso = usuarioController.atualizarSenha(userEmailToReset, novaSenha);

        if (sucesso) {
            GerenciadorTelas.exibirAlerta("Sucesso", "Sua senha foi redefinida com sucesso! Faça login com a nova senha.");
            // Redireciona para a tela de login
            Stage stage = (Stage) novaSenhaField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
        } else {
            GerenciadorTelas.exibirAlerta("Erro", "Não foi possível redefinir a senha. Tente novamente.");
        }
    }

    @FXML
    private void handleCancel() {
        // Redireciona para a tela de login
        Stage stage = (Stage) novaSenhaField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }
}