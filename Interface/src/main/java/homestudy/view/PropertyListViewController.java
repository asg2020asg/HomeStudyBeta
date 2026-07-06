package homestudy.view;

import homestudy.dao.ImovelDao;
import homestudy.model.Imovel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent; // Import para MouseEvent

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertyListViewController {

    @FXML private FlowPane propertyContainer;

    private MainViewController mainViewController; // Referência para MainViewController

    // Setter para MainViewController
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void initialize() {
        // A lista será carregada via loadImoveis() chamado pelo MainViewController
        // ou por uma chamada inicial aqui se não houver busca/filtro
        // Para garantir que algo apareça na inicialização, chamamos sem filtros
        loadImoveis(null, null);
    }

    // Método público para ser chamado pelo MainViewController
    public void loadImoveis(String searchQuery, Map<String, String> filters) {
        List<Imovel> imoveis = new ArrayList<>();
        try {
            imoveis = new ImovelDao().listarComFiltro(searchQuery, filters);
            if (imoveis.isEmpty()) {
                // Se não encontrar no banco, tenta carregar exemplos
                imoveis = criarImoveisExemplo();
            }
        } catch (RuntimeException e) {
            System.err.println("Nao foi possivel carregar imoveis do banco: " + e.getMessage());
            // Em caso de erro no banco, carrega exemplos
            imoveis = criarImoveisExemplo();
        }

        propertyContainer.getChildren().clear(); // Limpa os cards existentes

        if (imoveis.isEmpty()) {
            Label noResultsLabel = new Label("Nenhum imóvel encontrado com os critérios de busca/filtro.");
            noResultsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");
            propertyContainer.getChildren().add(noResultsLabel);
        } else {
            for (Imovel imovel : imoveis) {
                propertyContainer.getChildren().add(criarCard(imovel));
            }
        }
    }

    private List<Imovel> criarImoveisExemplo() {
        List<Imovel> exemplo = new ArrayList<>();
        // Adicionando o tipoImovel aos exemplos
        exemplo.add(new Imovel("Casa Estudantil", "Morada Nova", "2 quartos, perto da universidade", "850.00", "Casa"));
        exemplo.add(new Imovel("Apartamento Centro", "Centro", "Mobiliado, internet inclusa", "1100.00", "Apartamento"));
        exemplo.add(new Imovel("Kitnet Individual", "Bairro Universitario", "Ambiente compacto para estudante", "750.00", "Kitnet"));
        exemplo.add(new Imovel("Republica Compartilhada", "Morada Nova", "Quarto individual em casa compartilhada", "650.00", "Republica"));
        return exemplo;
    }

    private VBox criarCard(Imovel imovel) {
        VBox card = new VBox(8.0);
        card.setPrefWidth(240.0);
        card.setMinHeight(190.0);
        card.setStyle("-fx-background-color: white; -fx-border-color: #d6d6d6; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 14;");

        // Adiciona evento de clique ao card
        card.setOnMouseClicked(event -> {
            if (mainViewController != null) {
                mainViewController.loadPropertyDetail(imovel);
            } else {
                System.err.println("MainViewController é nulo. Não foi possível carregar detalhes do imóvel.");
            }
        });


        VBox imageBox = new VBox();
        imageBox.setPrefHeight(82.0);
        imageBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #d7ecff, #c9a986); -fx-border-color: #eeeeee;");

        Label title = new Label(valorOuPadrao(imovel.getNomeImovel(), "Imovel"));
        title.setWrapText(true);
        title.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #222;");

        Label address = new Label(valorOuPadrao(imovel.getEndereco(), "Endereco nao informado"));
        address.setWrapText(true);
        address.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        Label price = new Label("R$ " + valorOuPadrao(imovel.getValorImovel(), "0.00"));
        price.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d92828;");

        // Opcional: Adicionar o tipo de imóvel ao card
        Label type = new Label(valorOuPadrao(imovel.getTipoImovel(), "Tipo nao informado"));
        type.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");


        card.getChildren().addAll(imageBox, title, address, price, type); // Adicionado 'type'
        return card;
    }

    private String valorOuPadrao(String valor, String padrao) {
        if (valor == null || valor.isBlank()) {
            return padrao;
        }
        return valor;
    }
}