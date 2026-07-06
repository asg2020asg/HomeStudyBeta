package homestudy.view;

import homestudy.app.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RegisterChoiceViewController {

    @FXML
    private void handleAlunoRegister() {
        // Lógica para ir para a tela de cadastro de Aluno
        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/aluno-register-view.fxml", "Cadastro de Aluno");
    }

    @FXML
    private void handleProprietarioRegister() {
        // Lógica para ir para a tela de cadastro de Proprietário
        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/proprietario-register-view.fxml", "Cadastro de Proprietário");
    }

    @FXML
    private void handleBackButton() {
        // Lógica para voltar para a tela inicial
        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
    }
}
