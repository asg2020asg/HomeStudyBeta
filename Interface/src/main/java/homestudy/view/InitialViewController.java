package homestudy.view;

import homestudy.app.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InitialViewController {

    @FXML
    private void handleLoginButton() {
        // Lógica para ir para a tela de Login
        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }

    @FXML
    private void handleRegisterButton() {
        // Lógica para ir para a tela de Escolha de Cadastro (Aluno ou Proprietário)
        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/register-choice-view.fxml", "Cadastro HomeStudy");
    }
}
