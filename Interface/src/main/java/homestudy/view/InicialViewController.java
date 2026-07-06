package homestudy.view;

import homestudy.app.GerenciadorTelas;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InicialViewController {

    @FXML
    private void handleLoginButton() {

        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }

    @FXML
    private void handleRegisterButton() {

        Stage stage = (Stage) GerenciadorTelas.getPrimaryStage().getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/registro-escolha-view.fxml", "Cadastro HomeStudy");
    }
}
