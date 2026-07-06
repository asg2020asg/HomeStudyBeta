package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.dao.ProprietarioDao; // Importar ProprietarioDao
import homestudy.model.Imovel;
import homestudy.model.Proprietario; // Importar Proprietario
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetalhesImovelViewController {

    @FXML private Label propertyTitleLabel;
    @FXML private Label nomeImovelLabel;
    @FXML private Label enderecoLabel;
    @FXML private Label tipoImovelLabel;
    @FXML private Label informacaoImovelLabel;
    @FXML private Label valorImovelLabel;


    @FXML private Label proprietarioNomeLabel;
    @FXML private Label proprietarioEmailLabel;
    @FXML private Label proprietarioTelefoneLabel;


    private Imovel imovel;
    private MainViewController mainViewController; // Referência para MainViewController
    private ProprietarioDao proprietarioDao = new ProprietarioDao(); // Instância do DAO


    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
        if (imovel != null) {
            propertyTitleLabel.setText(imovel.getNomeImovel());
            nomeImovelLabel.setText(imovel.getNomeImovel());
            enderecoLabel.setText(imovel.getEndereco());
            tipoImovelLabel.setText(imovel.getTipoImovel());
            informacaoImovelLabel.setText(imovel.getInformacaoImovel());
            valorImovelLabel.setText("R$ " + imovel.getValorImovel());


            Proprietario proprietario = proprietarioDao.buscarPorId(imovel.getProprietarioId());
            if (proprietario != null) {
                proprietarioNomeLabel.setText(proprietario.getNome());
                proprietarioEmailLabel.setText(proprietario.getEmail());
                proprietarioTelefoneLabel.setText(proprietario.getTelefone());
            } else {
                proprietarioNomeLabel.setText("Não informado");
                proprietarioEmailLabel.setText("Não informado");
                proprietarioTelefoneLabel.setText("Não informado");
            }
        }
    }

    @FXML
    private void handleBackButton() {
        if (mainViewController != null) {

            mainViewController.loadPropertyList(mainViewController.getSearchTextField().getText(), mainViewController.getCurrentFilters());
        } else {

            Stage stage = (Stage) nomeImovelLabel.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "HomeStudy Beta");
        }
    }
}