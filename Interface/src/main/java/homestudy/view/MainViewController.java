package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.model.Usuario; // Assumindo que você tem uma classe Usuario
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class MainViewController {

    @FXML private VBox sidebar;
    @FXML private StackPane contentArea;
    @FXML private Button profileButton;
    @FXML private Button searchButton;
    @FXML private Button mapButton;

    private boolean sidebarOpen = false;
    private double sidebarWidth = 200.0; // Largura desejada da sidebar quando aberta

    private Usuario usuarioLogado; // Para armazenar o usuário logado

    // Método para injetar o usuário logado na tela principal
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        // Aqui você pode, por exemplo, atualizar o texto de um label com o nome do usuário
        // ou passar o usuário para as telas de conteúdo quando elas forem carregadas.
        System.out.println("Usuário logado na MainViewController: " + usuario.getNome());
    }

    @FXML
    public void initialize() {
        // Configura a largura inicial da sidebar para 0 (fechada)
        sidebar.setPrefWidth(0);
        sidebar.setMinWidth(0);
        sidebar.setMaxWidth(0);

        // REMOVIDO: Listeners de mouse da sidebar VBox, pois ela começa com largura 0
        // sidebar.setOnMouseEntered(event -> openSidebar());
        // sidebar.setOnMouseExited(event -> closeSidebar());

        // Carrega a tela de busca de imóveis como padrão ao iniciar
        showPropertySearch();
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
    private void showProfile() {
        loadContent("/homestudy/view/profile-view.fxml", "profile");
    }

    @FXML
    private void showPropertySearch() {
        loadContent("/homestudy/view/property-search-view.fxml", "propertySearch");
    }

    @FXML
    private void showMap() {
        loadContent("/homestudy/view/map-view.fxml", "map");
    }

    private void loadContent(String fxmlPath, String viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Aqui você pode passar o usuário logado para o controller da sub-tela, se necessário
            // Exemplo:
            // if (viewType.equals("profile") && loader.getController() instanceof ProfileViewController) {
            //     ProfileViewController controller = loader.getController();
            //     controller.setUsuario(usuarioLogado);
            // }

            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o conteúdo da tela: " + fxmlPath);
            e.printStackTrace();
            GerenciadorTelas.exibirAlerta("Erro", "Não foi possível carregar a tela. " + e.getMessage());
        }
    }
}
