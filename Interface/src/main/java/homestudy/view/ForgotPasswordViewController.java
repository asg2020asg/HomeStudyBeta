package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.controller.UsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForgotPasswordViewController {

    @FXML private TextField emailField;
    @FXML private TextField dataNascimentoField;
    @FXML private Label labelMensagem;

    private UsuarioController usuarioController = new UsuarioController();

    @FXML
    private void handleResetPassword() {
        String email = emailField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();

        if (email.isEmpty() || dataNascimentoStr.isEmpty()) {
            exibirAlerta("Aviso", "Por favor, preencha todos os campos.");
            return;
        }

        Date dataNascimento = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            dataNascimento = formatter.parse(dataNascimentoStr);
        } catch (ParseException e) {
            exibirAlerta("Erro de Formato", "Formato de data inválido. Use DD/MM/AAAA.");
            return;
        }

        // TODO: Implementar a lógica de redefinição de senha no UsuarioController
        // Por enquanto, apenas um placeholder
        // if (usuarioController.verificarDadosParaRedefinirSenha(email, dataNascimento)) {
        //     exibirAlerta("Sucesso", "Um link para redefinição de senha foi enviado para o seu e-mail.");
        // } else {
        //     exibirAlerta("Erro", "E-mail ou data de nascimento incorretos.");
        // }
        exibirAlerta("Funcionalidade em Desenvolvimento", "A redefinição de senha ainda não está implementada.");
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
