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
    public void initialize() {
        // Máscara para Telefone: (XX) XXXXX-XXXX
        telefoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            String cleanValue = newValue.replaceAll("[^\\d]", ""); // Remove tudo que não for dígito
            StringBuilder formattedValue = new StringBuilder();

            if (cleanValue.length() > 0) {
                formattedValue.append("(");
                if (cleanValue.length() > 2) {
                    formattedValue.append(cleanValue.substring(0, 2)).append(") ");
                    if (cleanValue.length() > 7) {
                        formattedValue.append(cleanValue.substring(2, 7)).append("-");
                        formattedValue.append(cleanValue.substring(7, Math.min(cleanValue.length(), 11)));
                    } else {
                        formattedValue.append(cleanValue.substring(2, Math.min(cleanValue.length(), 7)));
                    }
                } else {
                    formattedValue.append(cleanValue);
                }
            }

            // Limita o tamanho máximo da entrada (11 dígitos + caracteres da máscara)
            if (formattedValue.length() > 15) { // (XX) XXXXX-XXXX -> 15 caracteres
                formattedValue.setLength(15);
            }

            // Evita loop infinito
            if (!newValue.equals(formattedValue.toString())) {
                telefoneField.setText(formattedValue.toString());
                // Mantém o cursor no final
                telefoneField.positionCaret(formattedValue.length());
            }
        });

        // Máscara para Data de Nascimento: DD/MM/AAAA
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

            // Limita o tamanho máximo da entrada (8 dígitos + 2 barras)
            if (formattedValue.length() > 10) { // DD/MM/AAAA -> 10 caracteres
                formattedValue.setLength(10);
            }

            // Evita loop infinito
            if (!newValue.equals(formattedValue.toString())) {
                dataNascimentoField.setText(formattedValue.toString());
                // Mantém o cursor no final
                dataNascimentoField.positionCaret(formattedValue.length());
            }
        });
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

        // Remove a máscara antes de tentar fazer o parse da data
        String dataNascimentoClean = dataNascimentoStr.replaceAll("[^\\d]", "");
        if (dataNascimentoClean.length() != 8) {
            exibirAlerta("Erro de Formato", "Data de nascimento incompleta. Use DD/MM/AAAA.");
            return;
        }
        dataNascimentoStr = dataNascimentoClean.substring(0,2) + "/" + dataNascimentoClean.substring(2,4) + "/" + dataNascimentoClean.substring(4,8);


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