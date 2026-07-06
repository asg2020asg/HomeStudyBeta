package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.controller.UsuarioController;
import homestudy.model.Usuario;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Importar PasswordField

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EsqueceuSenhaViewController {

    @FXML private TextField emailField;
    @FXML private TextField dataNascimentoField;
    @FXML private PasswordField novaSenhaField; // NOVO
    @FXML private PasswordField repetirSenhaField; // NOVO
    @FXML private Label labelMensagem;

    private UsuarioController usuarioController = new UsuarioController();

    @FXML
    public void initialize() {

        dataNascimentoField.textProperty().addListener((observable, oldValue, newValue) -> {
            String cleanValue = newValue.replaceAll("[^\\d]", ""); // Remove tudo que não for dígito
            StringBuilder formattedValue = new StringBuilder();

            if (cleanValue.length() > 0) {
                if (cleanValue.length() > 2) {
                    formattedValue.append(cleanValue.substring(0, 2)).append("/");
                    if (cleanValue.length() > 4) {
                        formattedValue.append(cleanValue.substring(2, 4)).append("/");
                        formattedValue.append(cleanValue.substring(4, Math.min(cleanValue.length(), 8)));
                    } else {
                        formattedValue.append(cleanValue.substring(2, Math.min(cleanValue.length(), 4)));
                    }
                } else {
                    formattedValue.append(cleanValue);
                }
            }


            if (formattedValue.length() > 10) {
                formattedValue.setLength(10);
            }

            if (!newValue.equals(formattedValue.toString())) {
                dataNascimentoField.setText(formattedValue.toString());

                dataNascimentoField.positionCaret(formattedValue.length());
            }
        });
    }

    @FXML
    private void handleResetPassword() {
        String email = emailField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();
        String novaSenha = novaSenhaField.getText(); // NOVO
        String repetirSenha = repetirSenhaField.getText(); // NOVO


        if (email.isEmpty() || dataNascimentoStr.isEmpty()) {
            GerenciadorTelas.exibirAlerta("Aviso", "Por favor, preencha o e-mail e a data de nascimento.");
            return;
        }


        String dataNascimentoClean = dataNascimentoStr.replaceAll("[^\\d]", "");
        if (dataNascimentoClean.length() != 8) {
            GerenciadorTelas.exibirAlerta("Erro de Formato", "Data de nascimento incompleta. Use DD/MM/AAAA.");
            return;
        }
        dataNascimentoStr = dataNascimentoClean.substring(0,2) + "/" + dataNascimentoClean.substring(2,4) + "/" + dataNascimentoClean.substring(4,8);

        Date dataNascimento = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            dataNascimento = formatter.parse(dataNascimentoStr);
        } catch (ParseException e) {
            GerenciadorTelas.exibirAlerta("Erro de Formato", "Formato de data inválido. Use DD/MM/AAAA.");
            return;
        }


        Usuario usuarioVerificado = usuarioController.verificarEmailDataNascimento(email, dataNascimento);

        if (usuarioVerificado == null) {
            GerenciadorTelas.exibirAlerta("Erro de Verificação", "E-mail ou data de nascimento incorretos. A senha não será redefinida.");
            return;
        }


        if (novaSenha.isEmpty() || repetirSenha.isEmpty()) {
            GerenciadorTelas.exibirAlerta("Aviso", "Por favor, preencha os campos de 'Nova Senha' e 'Repetir Nova Senha'.");
            return;
        }

        if (!novaSenha.equals(repetirSenha)) {
            GerenciadorTelas.exibirAlerta("Erro", "As senhas não coincidem. Por favor, digite novamente.");
            return;
        }


        boolean sucesso = usuarioController.atualizarSenha(email, novaSenha);

        if (sucesso) {
            GerenciadorTelas.exibirAlerta("Sucesso", "Sua senha foi redefinida com sucesso! Faça login com a nova senha.");

            Stage stage = (Stage) emailField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
        } else {
            GerenciadorTelas.exibirAlerta("Erro", "Não foi possível redefinir a senha. Tente novamente.");
        }
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }
}