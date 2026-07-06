package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.dao.ImovelDao;
import homestudy.dao.ProprietarioDao;
import homestudy.model.Imovel;
import homestudy.model.Proprietario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProprietarioImovelRegisterViewController {

    @FXML private TextField nomeImovelField;
    @FXML private TextField enderecoField;
    @FXML private TextArea informacaoImovelArea;
    @FXML private TextField valorImovelField;
    @FXML private ComboBox<String> tipoImovelComboBox; // NOVO: ComboBox para tipo de imóvel
    @FXML private Label labelMensagem;
    @FXML private Label tituloLabel;
    @FXML private Button btnFinalizar;

    private ProprietarioDao proprietarioDao = new ProprietarioDao();
    private ImovelDao imovelDao = new ImovelDao();

    private Proprietario proprietarioLogado;
    private MainViewController mainViewController;
    private boolean modoAdicionarApenas = false;

    public void setProprietarioLogado(Proprietario proprietario, MainViewController mainViewController) {
        this.proprietarioLogado = proprietario;
        this.mainViewController = mainViewController;
        this.modoAdicionarApenas = true;
        
        if (tituloLabel != null) {
            tituloLabel.setText("Adicionar Novo Imóvel");
        }
        if (btnFinalizar != null) {
            btnFinalizar.setText("Adicionar Imóvel");
        }
    }

    @FXML
    public void initialize() {
        // Popula o ComboBox com as opções de tipo de imóvel
        tipoImovelComboBox.setItems(FXCollections.observableArrayList(
                "Casa", "Apartamento", "Kitnet", "Republica"
        ));
    }

    @FXML
    private void handleFinishRegister() {
        String nomeImovel = nomeImovelField.getText();
        String endereco = enderecoField.getText();
        String informacaoImovel = informacaoImovelArea.getText();
        String valorImovel = valorImovelField.getText();
        String tipoImovel = tipoImovelComboBox.getValue(); // NOVO: Obtém o tipo de imóvel selecionado

        if (nomeImovel.isEmpty() || endereco.isEmpty() || valorImovel.isEmpty() || tipoImovel == null || tipoImovel.isEmpty()) {
            exibirAlerta("Aviso", "Por favor, preencha todos os campos obrigatórios do imóvel, incluindo o tipo.");
            return;
        }

        try {
            Double.parseDouble(valorImovel); // Apenas para validar se é um número
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O valor do imóvel deve ser um número válido.");
            return;
        }

        Proprietario proprietario;
        if (modoAdicionarApenas) {
            proprietario = this.proprietarioLogado;
        } else {
            proprietario = ProprietarioRegisterViewController.getProprietarioEmCadastro();
        }

        if (proprietario == null) {
            exibirAlerta("Erro", "Dados do proprietário não encontrados. Por favor, reinicie o cadastro.");
            Stage stage = (Stage) nomeImovelField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/initial-view.fxml", "HomeStudy Beta");
            return;
        }

        try {
            if (!modoAdicionarApenas) {
                // 1. Persistir o Proprietário (que já inclui a inserção na tabela 'usuario')
                // O proprietarioDao.inserir já define o ID gerado no objeto 'proprietario'
                proprietarioDao.inserir(proprietario);

                // --- DEBUG: Verificar o ID do proprietário após a inserção ---
                System.out.println("DEBUG: Proprietário inserido com ID: " + proprietario.getId());
                if (proprietario.getId() <= 0) {
                    throw new RuntimeException("Falha crítica: ID do proprietário não foi gerado ou é inválido após a inserção.");
                }

                // --- DEBUG: Tentar buscar o proprietário recém-criado para verificar a persistência ---
                Proprietario proprietarioVerificado = proprietarioDao.buscarPorEmail(proprietario.getEmail());
                if (proprietarioVerificado != null && proprietarioVerificado.getId() == proprietario.getId()) {
                    System.out.println("DEBUG: Proprietário (ID: " + proprietarioVerificado.getId() + ") encontrado no DB após inserção.");
                } else {
                    System.err.println("DEBUG: ERRO! Proprietário (ID: " + proprietario.getId() + ") NÃO encontrado no DB após inserção ou ID não corresponde.");
                    throw new RuntimeException("Falha na persistência do proprietário. O ID não foi encontrado no banco de dados.");
                }
            }

            // 2. Criar o objeto Imovel e associá-lo ao proprietário
            // NOVO: Passando o tipoImovel para o construtor
            Imovel novoImovel = new Imovel(nomeImovel, endereco, informacaoImovel, valorImovel, tipoImovel);
            novoImovel.setProprietarioId(proprietario.getId()); // Define o ID do proprietário no imóvel

            // --- DEBUG: Verificar o proprietarioId do imóvel antes de cadastrar ---
            System.out.println("DEBUG: Imóvel sendo cadastrado com proprietarioId: " + novoImovel.getProprietarioId());

            // 3. Persistir o Imóvel no banco de dados
            imovelDao.cadastrar(novoImovel);

            if (modoAdicionarApenas) {
                exibirAlerta("Sucesso", "Novo imóvel cadastrado com sucesso!");
                // Retornar para a lista de imóveis na área principal
                if (mainViewController != null) {
                    mainViewController.showHome();
                }
            } else {
                exibirAlerta("Sucesso", "Cadastro de Proprietário e Imóvel finalizado com sucesso!");
                // Redirecionar para a tela de login
                Stage stage = (Stage) nomeImovelField.getScene().getWindow();
                GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
            }

        } catch (Exception e) {
            exibirAlerta("Erro de Cadastro", "Ocorreu um erro ao finalizar o cadastro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        if (modoAdicionarApenas) {
            if (mainViewController != null) {
                mainViewController.showHome();
            }
        } else {
            Stage stage = (Stage) nomeImovelField.getScene().getWindow();
            GerenciadorTelas.mudarTela(stage, "/homestudy/view/proprietario-register-view.fxml", "Cadastro de Proprietário");
        }
    }

    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}