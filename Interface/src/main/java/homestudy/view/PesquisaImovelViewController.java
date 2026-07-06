package homestudy.view;

import homestudy.app.GerenciadorTelas;
import homestudy.dao.ImovelDao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PesquisaImovelViewController {

    @FXML private ComboBox<String> bairroComboBox;
    @FXML private CheckBox casaCheckBox;
    @FXML private CheckBox kitnetCheckBox;
    @FXML private CheckBox apartamentoCheckBox;
    @FXML private CheckBox republicaCheckBox;
    @FXML private TextField precoMinField;
    @FXML private TextField precoMaxField;

    private MainViewController mainViewController;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void initialize() {

        populateBairroComboBox();


        casaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                kitnetCheckBox.setSelected(false);
                apartamentoCheckBox.setSelected(false);
                republicaCheckBox.setSelected(false);
            }
        });
        kitnetCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                casaCheckBox.setSelected(false);
                apartamentoCheckBox.setSelected(false);
                republicaCheckBox.setSelected(false);
            }
        });
        apartamentoCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                casaCheckBox.setSelected(false);
                kitnetCheckBox.setSelected(false);
                republicaCheckBox.setSelected(false);
            }
        });
        republicaCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                casaCheckBox.setSelected(false);
                kitnetCheckBox.setSelected(false);
                apartamentoCheckBox.setSelected(false);
            }
        });
    }

    private void populateBairroComboBox() {
        Set<String> bairros = new HashSet<>();
        try {
            new ImovelDao().listarTodos().forEach(imovel -> {
                String endereco = imovel.getEndereco();
                if (endereco != null && !endereco.isBlank()) {
                    bairros.add(endereco);
                }
            });
        } catch (RuntimeException e) {
            System.err.println("Erro ao carregar bairros do banco de dados: " + e.getMessage());
        }

        bairroComboBox.setItems(FXCollections.observableArrayList(bairros));
    }

    public void setCurrentFilters(Map<String, String> filters) {
        if (filters == null) return;


        if (filters.containsKey("bairro")) {
            bairroComboBox.getSelectionModel().select(filters.get("bairro"));
        }


        if (filters.containsKey("tipoImovel")) {
            String tipo = filters.get("tipoImovel");
            casaCheckBox.setSelected("Casa".equalsIgnoreCase(tipo));
            kitnetCheckBox.setSelected("Kitnet".equalsIgnoreCase(tipo));
            apartamentoCheckBox.setSelected("Apartamento".equalsIgnoreCase(tipo));
            republicaCheckBox.setSelected("Republica".equalsIgnoreCase(tipo));
        }


        if (filters.containsKey("valorMin")) {
            precoMinField.setText(filters.get("valorMin"));
        }
        if (filters.containsKey("valorMax")) {
            precoMaxField.setText(filters.get("valorMax"));
        }
    }

    @FXML
    private void handleApplyFilters() {
        Map<String, String> filters = new HashMap<>();


        String selectedBairro = bairroComboBox.getSelectionModel().getSelectedItem();
        if (selectedBairro != null && !selectedBairro.isBlank()) {
            filters.put("bairro", selectedBairro);
        }


        if (casaCheckBox.isSelected()) {
            filters.put("tipoImovel", "Casa");
        } else if (kitnetCheckBox.isSelected()) {
            filters.put("tipoImovel", "Kitnet");
        } else if (apartamentoCheckBox.isSelected()) {
            filters.put("tipoImovel", "Apartamento");
        } else if (republicaCheckBox.isSelected()) {
            filters.put("tipoImovel", "Republica");
        }


        String precoMin = precoMinField.getText();
        if (precoMin != null && !precoMin.isBlank()) {
            try {
                Double.parseDouble(precoMin); // Valida se é um número
                filters.put("valorMin", precoMin);
            } catch (NumberFormatException e) {
                GerenciadorTelas.exibirAlerta("Erro de Entrada", "O valor mínimo do preço deve ser um número válido.");
                return;
            }
        }


        String precoMax = precoMaxField.getText();
        if (precoMax != null && !precoMax.isBlank()) {
            try {
                Double.parseDouble(precoMax); // Valida se é um número
                filters.put("valorMax", precoMax);
            } catch (NumberFormatException e) {
                GerenciadorTelas.exibirAlerta("Erro de Entrada", "O valor máximo do preço deve ser um número válido.");
                return;
            }
        }

        if (mainViewController != null) {
            mainViewController.applyFilters(filters);
        }
    }

    @FXML
    private void handleCancel() {

        if (mainViewController != null) {
            mainViewController.loadPropertyList(mainViewController.getSearchTextField().getText(), mainViewController.getCurrentFilters());
        }
    }
}