package homestudy.view;

import homestudy.dao.ImovelDao;
import homestudy.model.Aluno;
import homestudy.model.Imovel;
import homestudy.model.Proprietario;
import homestudy.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProfileViewController {

    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label birthLabel;
    @FXML private Label extraLabel;
    @FXML private Label propertiesTitleLabel;
    @FXML private VBox propertiesBox;

    public void setUsuario(Usuario usuario) {
        propertiesBox.getChildren().clear();

        if (usuario == null) {
            nameLabel.setText("Perfil");
            typeLabel.setText("Usuario nao carregado");
            emailLabel.setText("E-mail: -");
            phoneLabel.setText("Telefone: -");
            birthLabel.setText("Data de nascimento: -");
            extraLabel.setText("");
            propertiesTitleLabel.setText("Imoveis");
            propertiesBox.getChildren().add(new Label("Entre novamente para carregar as informacoes do perfil."));
            return;
        }

        nameLabel.setText(usuario.getNome());
        emailLabel.setText("E-mail: " + valorOuPadrao(usuario.getEmail(), "-"));
        phoneLabel.setText("Telefone: " + valorOuPadrao(usuario.getTelefone(), "-"));
        birthLabel.setText("Data de nascimento: " + formatarData(usuario));

        if (usuario instanceof Aluno aluno) {
            typeLabel.setText("Aluno");
            extraLabel.setText("Curso: " + valorOuPadrao(aluno.getCurso(), "-") + " | Periodo: " + valorOuPadrao(aluno.getPeriodo(), "-"));
            propertiesTitleLabel.setText("Interesses");
            propertiesBox.getChildren().add(new Label("Use a tela Principal ou Radar para encontrar moradias disponiveis."));
            return;
        }

        if (usuario instanceof Proprietario) {
            typeLabel.setText("Proprietario");
            extraLabel.setText("");
            propertiesTitleLabel.setText("Meus imoveis");
            carregarImoveisDoProprietario(usuario.getId());
            return;
        }

        typeLabel.setText("Usuario");
        extraLabel.setText("");
        propertiesTitleLabel.setText("Imoveis");
        propertiesBox.getChildren().add(new Label("Nenhum dado adicional para este tipo de usuario."));
    }

    private void carregarImoveisDoProprietario(int proprietarioId) {
        try {
            List<Imovel> imoveis = new ImovelDao().listarTodos()
                    .stream()
                    .filter(imovel -> imovel.getProprietarioId() == proprietarioId)
                    .toList();

            if (imoveis.isEmpty()) {
                propertiesBox.getChildren().add(new Label("Nenhum imovel cadastrado para este proprietario."));
                return;
            }

            for (Imovel imovel : imoveis) {
                Label item = new Label(imovel.getNomeImovel() + " - " + imovel.getEndereco() + " - R$ " + imovel.getValorImovel());
                item.setWrapText(true);
                item.setStyle("-fx-font-size: 14px;");
                propertiesBox.getChildren().add(item);
            }
        } catch (RuntimeException e) {
            propertiesBox.getChildren().add(new Label("Nao foi possivel carregar os imoveis agora."));
        }
    }

    private String formatarData(Usuario usuario) {
        if (usuario.getDataNascimento() == null) {
            return "-";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(usuario.getDataNascimento());
    }

    private String valorOuPadrao(String valor, String padrao) {
        if (valor == null || valor.isBlank()) {
            return padrao;
        }
        return valor;
    }
}
