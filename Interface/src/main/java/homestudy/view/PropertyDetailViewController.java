package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.dao.ProprietarioDao; // Importar ProprietarioDao
import homestudy.model.Imovel;
import homestudy.model.Proprietario; // Importar Proprietario
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PropertyDetailViewController {

    @FXML private Label propertyTitleLabel;
    @FXML private Label nomeImovelLabel;
    @FXML private Label enderecoLabel;
    @FXML private Label tipoImovelLabel;
    @FXML private Label informacaoImovelLabel;
    @FXML private Label valorImovelLabel;

    // NOVO: Labels para informações do proprietário
    @FXML private Label proprietarioNomeLabel;
    @FXML private Label proprietarioEmailLabel;
    @FXML private Label proprietarioTelefoneLabel;


    private Imovel imovel; // Para armazenar o imóvel exibido
    private MainViewController mainViewController; // Referência para MainViewController
    private ProprietarioDao proprietarioDao = new ProprietarioDao(); // Instância do DAO

    // Setter para MainViewController
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

            // NOVO: Buscar e exibir informações do proprietário
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
            // Retorna para a lista de imóveis, mantendo a busca e os filtros atuais
            mainViewController.loadPropertyList(mainViewController.getSearchTextField().getText(), mainViewController.getCurrentFilters());
        } else {
            // Fallback: se por algum motivo a referência for nula, volta para a tela de login
            Stage stage = (Stage) nomeImovelLabel.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "HomeStudy Beta");
        }
    }
}