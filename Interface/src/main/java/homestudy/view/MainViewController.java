package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.model.Usuario;
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
    private final double sidebarWidth = 200.0;
    private Usuario usuarioLogado;

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
    private void showHome() {
        loadContent("/homestudy/view/property-list-view.fxml");
    }

    @FXML
    private void showProfile() {
        loadContent("/homestudy/view/profile-view.fxml");
    }

    @FXML
    private void showPropertySearch() {
        loadContent("/homestudy/view/property-search-view.fxml");
    }

    @FXML
    private void showMap() {
        loadContent("/homestudy/view/map-view.fxml");
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
}
