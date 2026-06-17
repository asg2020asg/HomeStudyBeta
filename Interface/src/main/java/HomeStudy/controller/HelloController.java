package homestudy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelMensagem;

    @FXML
    private void aoClicarEntrar() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        // Validação simples (Substitua pela sua lógica de banco de dados depois)
        if (usuario.isEmpty() || senha.isEmpty()) {
            labelMensagem.setTextFill(Color.RED);
            labelMensagem.setText("Por favor, preencha todos os campos!");
        } else if (usuario.equals("admin") && senha.equals("1234")) {
            labelMensagem.setTextFill(Color.GREEN);
            labelMensagem.setText("Login efetuado com sucesso!");

            // Aqui você colocaria o código para abrir a próxima tela (ex: tela-principal.fxml)
            System.out.println("Abrindo tela principal...");
        } else {
            labelMensagem.setTextFill(Color.RED);
            labelMensagem.setText("Usuário ou senha incorretos.");
        }
    }
}