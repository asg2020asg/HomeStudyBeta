package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.controller.AlunoController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlunoRegisterViewController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private TextField telefoneField;
    @FXML private PasswordField senhaField;
    @FXML private TextField dataNascimentoField;
    @FXML private TextField cursoField;
    @FXML private TextField periodoField;
    @FXML private Label labelMensagem;

    private AlunoController alunoController = new AlunoController();

    @FXML
    private void handleRegister() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();
        String senha = senhaField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();
        String curso = cursoField.getText();
        String periodo = periodoField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimentoStr.isEmpty() || curso.isEmpty() || periodo.isEmpty()) {
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

        try {
            alunoController.cadastrarAluno(nome, email, telefone, senha, dataNascimento, curso, periodo);
            exibirAlerta("Sucesso", "Aluno cadastrado com sucesso!");
            // Após o cadastro, pode-se redirecionar para a tela de login ou principal do aluno
            Stage stage = (Stage) nomeField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
        } catch (Exception e) {
            exibirAlerta("Erro de Cadastro", "Ocorreu um erro ao cadastrar o aluno: " + e.getMessage());
        }
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
