package homestudy.view;
import homestudy.controller.AlunoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;

public class CadastroAlunoController {
    @FXML private TextField txtNome, txtEmail, txtTelefone, txtCurso, txtPeriodo;
    @FXML private PasswordField txtSenha;
    @FXML private DatePicker dateNasc;

    private AlunoController alunoController = new AlunoController();

    @FXML
    private void salvarAluno() {
        alunoController.cadastrarAluno(txtNome.getText(), txtEmail.getText(),
                txtTelefone.getText(), txtSenha.getText(),
                Date.valueOf(dateNasc.getValue()), txtCurso.getText(), txtPeriodo.getText());
    }
}