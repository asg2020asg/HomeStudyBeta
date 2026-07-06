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

public class ImovelDao {

    public ImovelDao() {
        // Construtor vazio, não precisa mais da lista em memória
    }

    public int cadastrar(Imovel imovel) {
        String sql = "INSERT INTO imovel (proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, imovel.getProprietarioId());
            stmt.setString(2, imovel.getNomeImovel());
            stmt.setString(3, imovel.getEndereco());
            stmt.setString(4, imovel.getInformacaoImovel());
            stmt.setDouble(5, Double.parseDouble(imovel.getValorImovel())); // Converte String para Double

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    imovel.setId(generatedId); // Define o ID gerado no objeto Imovel
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
        List<Imovel> listaImoveis = new ArrayList<>();
        String sql = "SELECT id, proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel FROM imovel";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Imovel imovel = new Imovel(
                        rs.getInt("id"),
                        rs.getInt("proprietario_id"),
                        rs.getString("nome_imovel"),
                        rs.getString("endereco"),
                        rs.getString("informacao_imovel"),
                        String.valueOf(rs.getDouble("valor_imovel")) // Converte Double para String
                );
                listaImoveis.add(imovel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar imóveis do banco de dados", e);
        }
        return listaImoveis;
    }

    public Imovel buscarPorNome(String nome) {
        String sql = "SELECT id, proprietario_id, nome_imovel, endereco, informacao_imovel, valor_imovel FROM imovel WHERE nome_imovel = ?";
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
                            String.valueOf(rs.getDouble("valor_imovel"))
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
        String sql = "UPDATE imovel SET proprietario_id=?, nome_imovel=?, endereco=?, informacao_imovel=?, valor_imovel=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, imovel.getProprietarioId());
            stmt.setString(2, imovel.getNomeImovel());
            stmt.setString(3, imovel.getEndereco());
            stmt.setString(4, imovel.getInformacaoImovel());
            stmt.setDouble(5, Double.parseDouble(imovel.getValorImovel()));
            stmt.setInt(6, imovel.getId());

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
}
