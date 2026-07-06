package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.model.Imovel; // Importar Imovel
import homestudy.model.Usuario;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainViewController {

    @FXML private VBox sidebar;
    @FXML private StackPane contentArea;
    @FXML private Button profileButton;
    @FXML private Button mapButton;
    @FXML private TextField searchTextField; // Adicionado fx:id para o campo de busca

    private boolean sidebarOpen = false;
    private final double sidebarWidth = 200.0;
    private Usuario usuarioLogado;
    private Map<String, String> currentFilters = new HashMap<>(); // Para armazenar os filtros aplicados

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        if (usuario != null) {
            System.out.println("Usuario logado na MainViewController: " + usuario.getNome());
        }
    }

    @FXML
    public void initialize() {
        sidebar.setPrefWidth(0);
        sidebar.setMinWidth(0);
        sidebar.setMaxWidth(0);
        showHome(); // Carrega a lista de imóveis inicialmente
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
    private void showHome() {
        // Ao voltar para a home, limpa a busca e os filtros e recarrega a lista
        searchTextField.clear();
        currentFilters.clear();
        loadPropertyList(null, null);
    }

    @FXML
    private void showProfile() {
        loadContent("/homestudy/view/profile-view.fxml");
    }

    @FXML
    private void showPropertySearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/property-search-view.fxml"));
            Parent root = loader.load();
            PropertySearchViewController controller = loader.getController();
            controller.setMainViewController(this); // Passa a referência do MainViewController
            controller.setCurrentFilters(currentFilters); // Passa os filtros atuais para preencher a tela de filtro

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de busca de propriedades: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a tela de filtro. " + e.getMessage());
        }
    }

    @FXML
    private void showMap() {
        loadContent("/homestudy/view/map-view.fxml");
    }

    @FXML
    private void handleSearch() {
        String searchText = searchTextField.getText();
        loadPropertyList(searchText, currentFilters);
    }

    // Método para aplicar filtros recebidos do PropertySearchViewController
    public void applyFilters(Map<String, String> filters) {
        this.currentFilters = new HashMap<>(filters); // Atualiza os filtros atuais
        loadPropertyList(searchTextField.getText(), this.currentFilters); // Recarrega a lista com os novos filtros e busca atual
    }

    // Método para carregar a lista de propriedades com busca e filtros
    public void loadPropertyList(String searchQuery, Map<String, String> filters) { // Tornar public para acesso externo
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/property-list-view.fxml"));
            Parent root = loader.load();
            PropertyListViewController controller = loader.getController();
            controller.setMainViewController(this); // NOVO: Passa a referência do MainViewController
            controller.loadImoveis(searchQuery, filters); // Chama o método para carregar imóveis com busca e filtro

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a lista de propriedades: " + e.getMessage());
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a lista de imoveis. " + e.getMessage());
        }
    }

    // NOVO MÉTODO: Carrega a tela de detalhes de um imóvel
    public void loadPropertyDetail(Imovel imovel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homestudy/view/property-detail-view.fxml"));
            Parent root = loader.load();
            PropertyDetailViewController controller = loader.getController();
            controller.setImovel(imovel); // Passa o objeto Imovel para o controlador de detalhes
            controller.setMainViewController(this); // Passa a referência do MainViewController para o controlador de detalhes

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

            if (loader.getController() instanceof ProfileViewController controller) {
                controller.setUsuario(usuarioLogado);
            }

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o conteudo da tela: " + fxmlPath);
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Nao foi possivel carregar a tela. " + e.getMessage());
        }
    }

    // Novos métodos getters para acesso do PropertySearchViewController
    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Map<String, String> getCurrentFilters() {
        return currentFilters;
    }
}