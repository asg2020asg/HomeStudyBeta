package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.model.Imovel;
import homestudy.model.Usuario;
import homestudy.model.Proprietario;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainViewController {

    @FXML private VBox sidebar;
    @FXML private StackPane contentArea;
    @FXML private Button profileButton;
    @FXML private Button addPropertyButton;
    @FXML private Button mapButton;
    @FXML private TextField searchTextField;

    private boolean sidebarOpen = false;
    private final double sidebarWidth = 200.0;
    private Usuario usuarioLogado;
    private Map<String, String> currentFilters = new HashMap<>();

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            System.out.println("Usuario logado na MainViewController: " + usuario.getNome());
            if (usuario instanceof Proprietario) {
                addPropertyButton.setVisible(true);
                addPropertyButton.setManaged(true);
            } else {
                addPropertyButton.setVisible(false);
                addPropertyButton.setManaged(false);
            }
        }
    }

    @FXML
    public void initialize() {
        sidebar.setPrefWidth(0);
        sidebar.setMinWidth(0);
        sidebar.setMaxWidth(0);
        showHome();
    }

    @FXML
    private void toggleSidebar() {
        if (sidebarOpen) {
            closeSidebar();
        } else {
            openSidebar();
        }
    }

    private void openSidebar() {
        if (!sidebarOpen) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(sidebar.prefWidthProperty(), sidebar.getWidth()),
                            new KeyValue(sidebar.minWidthProperty(), sidebar.getMinWidth()),
                            new KeyValue(sidebar.maxWidthProperty(), sidebar.getMaxWidth())
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(sidebar.prefWidthProperty(), sidebarWidth),
                            new KeyValue(sidebar.minWidthProperty(), sidebarWidth),
                            new KeyValue(sidebar.maxWidthProperty(), sidebarWidth)
                    )
            );
            timeline.play();
            sidebarOpen = true;
        }
    }

    private void closeSidebar() {
        if (sidebarOpen) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(sidebar.prefWidthProperty(), sidebar.getWidth()),
                            new KeyValue(sidebar.minWidthProperty(), sidebar.getMinWidth()),
                            new KeyValue(sidebar.maxWidthProperty(), sidebar.getMaxWidth())
                    ),
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(sidebar.prefWidthProperty(), 0),
                            new KeyValue(sidebar.minWidthProperty(), 0),
                            new KeyValue(sidebar.maxWidthProperty(), 0)
                    )
            );
            timeline.play();
            sidebarOpen = false;
        }
    }

    @FXML
    public void showHome() {

        searchTextField.clear();
        currentFilters.clear();
        loadPropertyList(null, null);
    }

    @FXML
    private void showProfile() {
        loadContent("/homestudy/view/perfil-view.fxml");
    }

    @FXML
    private void showAddProperty() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/proprietario-imovel-registro-view.fxml"));
            Parent root = loader.load();
            ProprietarioImovelRegisterViewController controller = loader.getController();
            controller.setProprietarioLogado((Proprietario) usuarioLogado, this);
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de cadastro de imóvel: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Não foi possível carregar a tela de cadastro de imóvel: " + e.getMessage());
        }
    }

    @FXML
    private void showPropertySearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/imovel-pesquisa-view.fxml"));
            Parent root = loader.load();
            PesquisaImovelViewController controller = loader.getController();
            controller.setMainViewController(this);
            controller.setCurrentFilters(currentFilters);

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de busca de propriedades: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a tela de filtro. " + e.getMessage());
        }
    }

    @FXML
    private void showMap() {
        loadContent("/homestudy/view/mapa-view.fxml");
    }

    @FXML
    private void handleSearch() {
        String searchText = searchTextField.getText();
        loadPropertyList(searchText, currentFilters);
    }


    public void applyFilters(Map<String, String> filters) {
        this.currentFilters = new HashMap<>(filters);
        loadPropertyList(searchTextField.getText(), this.currentFilters);
    }


    public void loadPropertyList(String searchQuery, Map<String, String> filters) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/imovel-lista-view.fxml"));
            Parent root = loader.load();
            ListaImovelViewController controller = loader.getController();
            controller.setMainViewController(this);
            controller.loadImoveis(searchQuery, filters);

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a lista de propriedades: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a lista de imoveis. " + e.getMessage());
        }
    }


    public void loadPropertyDetail(Imovel imovel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/imovel-detalhe-view.fxml"));
            Parent root = loader.load();
            DetalhesImovelViewController controller = loader.getController();
            controller.setImovel(imovel);
            controller.setMainViewController(this);

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de detalhes do imóvel: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar os detalhes do imovel. " + e.getMessage());
        }
    }


    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (loader.getController() instanceof PerfilViewController controller) {
                controller.setUsuario(usuarioLogado);
            }

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o conteudo da tela: " + fxmlPath);
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a tela. " + e.getMessage());
        }
    }


    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Map<String, String> getCurrentFilters() {
        return currentFilters;
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        GerenciadorTelas.mudarTela(stage, "/homestudy/view/login-view.fxml", "Login HomeStudy");
    }
}