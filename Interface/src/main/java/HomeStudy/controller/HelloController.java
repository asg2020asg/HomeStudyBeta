package homestudy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label mensagemAlerta;

    @FXML
    void aoClicarEntrar() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mensagemAlerta.setText("Por favor, preencha todos os campos!");
        } else if (usuario.equals("admin") && senha.equals("123")) {
            try {
                // 1. Carrega o arquivo da nova tela (menu.fxml)
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/homestudy/menu.fxml"));
                Scene novaCena = new Scene(fxmlLoader.load(), 600, 400);

                // 2. Pega o "palco" (Stage) atual através de qualquer componente da tela (como o campoUsuario)
                Stage palcoAtual = (Stage) campoUsuario.getScene().getWindow();

                // 3. Substitui a cena da janela pela nova tela do menu
                palcoAtual.setScene(novaCena);
                palcoAtual.setTitle("HomeStudy - Menu Principal");

            } catch (IOException e) {
                mensagemAlerta.setText("Erro ao carregar a tela de menu!");
                e.printStackTrace();
            }
        } else {
            mensagemAlerta.setText("Usuário ou senha incorretos!");
        }
    }
}