package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.model.Proprietario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProprietarioRegisterViewController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private PasswordField senhaField;
    @FXML private TextField dataNascimentoField;
    @FXML private Label labelMensagem;

    // Temporarily store proprietor data to pass to the next screen
    private static Proprietario proprietarioEmCadastro;

    public static Proprietario getProprietarioEmCadastro() {
        return proprietarioEmCadastro;
    }

    @FXML
    private void handleNext() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();
        String senha = senhaField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimentoStr.isEmpty()) {
            exibirAlerta("Aviso", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            exibirAlerta("Erro de Validação", "Por favor, insira um e-mail válido.");
            return;
        }

        Date dataNascimento = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false); // Não permite datas inválidas como 30/02
            dataNascimento = formatter.parse(dataNascimentoStr);
        } catch (ParseException e) {
            exibirAlerta("Erro de Formato", "Formato de data inválido. Use DD/MM/AAAA.");
            return;
        }

        // Create a temporary Proprietario object with personal data
        proprietarioEmCadastro = new Proprietario(nome, email, telefone, senha, dataNascimento);

        // Navigate to the next registration step (property details)
        Stage stage = (Stage) nomeField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/proprietario-imovel-register-view.fxml", "Cadastro de Imóvel");
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/register-choice-view.fxml", "Cadastro HomeStudy");
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
