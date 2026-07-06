package homestudy.view;

import homestudy.dao.ImovelDao;
import homestudy.model.Imovel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PropertyListViewController {

    @FXML private FlowPane propertyContainer;

    @FXML
    public void initialize() {
        List<Imovel> imoveis = carregarImoveis();
        propertyContainer.getChildren().clear();

        for (Imovel imovel : imoveis) {
            propertyContainer.getChildren().add(criarCard(imovel));
        }
    }

    private List<Imovel> carregarImoveis() {
        try {
            List<Imovel> imoveis = new ImovelDao().listarTodos();
            if (!imoveis.isEmpty()) {
                return imoveis;
            }
        } catch (RuntimeException e) {
            System.err.println("Nao foi possivel carregar imoveis do banco: " + e.getMessage());
        }

        List<Imovel> exemplo = new ArrayList<>();
        exemplo.add(new Imovel("Casa Estudantil", "Morada Nova", "2 quartos, perto da universidade", "850.00"));
        exemplo.add(new Imovel("Apartamento Centro", "Centro", "Mobiliado, internet inclusa", "1100.00"));
        exemplo.add(new Imovel("Kitnet Individual", "Bairro Universitario", "Ambiente compacto para estudante", "750.00"));
        exemplo.add(new Imovel("Republica Compartilhada", "Morada Nova", "Quarto individual em casa compartilhada", "650.00"));
        return exemplo;
    }

    private VBox criarCard(Imovel imovel) {
        VBox card = new VBox(8.0);
        card.setPrefWidth(240.0);
        card.setMinHeight(190.0);
        card.setStyle("-fx-background-color: white; -fx-border-color: #d6d6d6; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 14;");

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

        card.getChildren().addAll(imageBox, title, address, price);
        return card;
    }

    private String valorOuPadrao(String valor, String padrao) {
        if (valor == null || valor.isBlank()) {
            return padrao;
        }
        return valor;
    }
}
