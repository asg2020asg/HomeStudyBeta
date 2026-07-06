package homestudy.view;

import homestudy.app.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RegistroEscolhaViewController {

    @FXML
    private void handleAlunoRegister() {

        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/aluno-registro-view.fxml", "Cadastro de Aluno");
    }

    @FXML
    private void handleProprietarioRegister() {

        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/proprietario-registro-view.fxml", "Cadastro de Proprietário");
    }

    @FXML
    private void handleBackButton() {

        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/inicio-view.fxml", "HomeStudy Beta");
    }
}