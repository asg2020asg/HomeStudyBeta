package homestudy.dao;

import homestudy.model.Imovel;
import homestudy.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImovelDao {

    public ImovelDao() {
        // Construtor vazio
    }

    public int cadastrar(Imovel imovel) {
        String sql = "INSERT INTO imovel (proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel, tipo_imovel) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, imovel.getProprietarioId());
            stmt.setString(2, imovel.getNomeImovel());
            stmt.setString(3, imovel.getEndereco());
            stmt.setString(4, imovel.getInformacaoImovel());
            stmt.setDouble(5, Double.parseDouble(imovel.getValorImovel()));
            stmt.setString(6, imovel.getTipoImovel()); // Novo campo

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    imovel.setId(generatedId);
                }
            }
            System.out.println("Imóvel '" + imovel.getNomeImovel() + "' cadastrado com sucesso no banco de dados!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar imóvel no banco de dados", e);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro de formato no valor do imóvel", e);
        }
        return generatedId;
    }

    public List<Imovel> listarTodos() {
        return listarComFiltro(null, null); // Chama o método com filtro sem parâmetros
    }

    public Imovel buscarPorNome(String nome) {
        String sql = "SELECT id, proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel, tipo_imovel FROM imovel WHERE nome_imovel = ?";
        Imovel imovel = null;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    imovel = new Imovel(
                            rs.getInt("id"),
                            rs.getInt("proprietario_id"),
                            rs.getString("nome_imovel"),
                            rs.getString("endereco"),
                            rs.getString("informacao_imovel"),
                            String.valueOf(rs.getDouble("valor_imovel")),
                            rs.getString("tipo_imovel") // Novo campo
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar imóvel por nome no banco de dados", e);
        }
        return imovel;
    }

    public boolean atualizar(Imovel imovel) {
        String sql = "UPDATE imovel SET proprietario_id=?, nome_imovel=?, endereco=?, informacao_imovel=?, valor_imovel=?, tipo_imovel=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, imovel.getProprietarioId());
            stmt.setString(2, imovel.getNomeImovel());
            stmt.setString(3, imovel.getEndereco());
            stmt.setString(4, imovel.getInformacaoImovel());
            stmt.setDouble(5, Double.parseDouble(imovel.getValorImovel()));
            stmt.setString(6, imovel.getTipoImovel()); // Novo campo
            stmt.setInt(7, imovel.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Imóvel atualizado com sucesso no banco de dados!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar imóvel no banco de dados", e);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro de formato no valor do imóvel ao atualizar", e);
        }
        System.out.println("Erro: Imóvel com o ID '" + imovel.getId() + "' não encontrado para atualização.");
        return false;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM imovel WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Imóvel removido com sucesso do banco de dados!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover imóvel do banco de dados", e);
        }
        System.out.println("Erro: Imóvel com o ID '" + id + "' não encontrado para exclusão.");
        return false;
    }

    /**
     * Lista imóveis com base em uma string de busca e filtros específicos.
     *
     * @param searchQuery Termo geral de busca (nome, endereço, informações). Pode ser null.
     * @param filters     Mapa de filtros, onde a chave é o nome do filtro e o valor é o critério. Pode ser null.
     *                    Ex: "bairro", "tipoImovel", "valorMin", "valorMax".
     * @return Lista de imóveis que correspondem aos critérios.
     */
    public List<Imovel> listarComFiltro(String searchQuery, Map<String, String> filters) {
        List<Imovel> listaImoveis = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel, tipo_imovel FROM imovel WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Adiciona a busca geral
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (nome_imovel LIKE ? OR endereco LIKE ? OR informacao_imovel LIKE ?)");
            String likeQuery = "%" + searchQuery.trim() + "%";
            params.add(likeQuery);
            params.add(likeQuery);
            params.add(likeQuery);
        }

        // Adiciona filtros específicos
        if (filters != null && !filters.isEmpty()) {
            // Filtro por bairro
            if (filters.containsKey("bairro") && !filters.get("bairro").isEmpty()) {
                sql.append(" AND endereco LIKE ?");
                params.add("%" + filters.get("bairro") + "%");
            }
            // Filtro por tipo de imóvel
            if (filters.containsKey("tipoImovel") && !filters.get("tipoImovel").isEmpty()) {
                sql.append(" AND tipo_imovel = ?");
                params.add(filters.get("tipoImovel"));
            }
            // Filtro por valor mínimo
            if (filters.containsKey("valorMin") && !filters.get("valorMin").isEmpty()) {
                try {
                    double valorMin = Double.parseDouble(filters.get("valorMin"));
                    sql.append(" AND valor_imovel >= ?");
                    params.add(valorMin);
                } catch (NumberFormatException e) {
                    System.err.println("Valor mínimo inválido: " + filters.get("valorMin"));
                }
            }
            // Filtro por valor máximo
            if (filters.containsKey("valorMax") && !filters.get("valorMax").isEmpty()) {
                try {
                    double valorMax = Double.parseDouble(filters.get("valorMax"));
                    sql.append(" AND valor_imovel <= ?");
                    params.add(valorMax);
                } catch (NumberFormatException e) {
                    System.err.println("Valor máximo inválido: " + filters.get("valorMax"));
                }
            }
        }

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Imovel imovel = new Imovel(
                            rs.getInt("id"),
                            rs.getInt("proprietario_id"),
                            rs.getString("nome_imovel"),
                            rs.getString("endereco"),
                            rs.getString("informacao_imovel"),
                            String.valueOf(rs.getDouble("valor_imovel")),
                            rs.getString("tipo_imovel")
                    );
                    listaImoveis.add(imovel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar imóveis com filtro do banco de dados", e);
        }
        return listaImoveis;
    }
}