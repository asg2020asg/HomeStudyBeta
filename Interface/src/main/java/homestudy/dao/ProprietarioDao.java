package homestudy.dao;

import homestudy.util.Conexao;
import homestudy.model.Proprietario;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;

public class ProprietarioDao {
    public void inserir(Proprietario proprietario) {
        String sqlInserir = "INSERT INTO proprietario (email) VALUES(?)";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInserir);
            stmt.setString(1, proprietario.getEmail());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Proprietario proprietario) {
        String sqlAtualizar = "UPDATE proprietario SET email=? WHERE email=?";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement((sqlAtualizar));

            stmt.setString(1, proprietario.getEmail());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Proprietario> listarTodos() {
        String sqlListar = "SELECT * FROM proprietario";
        List<Proprietario> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlListar);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                        "",
                        "",
                        rs.getString("email"),
                        "",
                        null

                );
                lista.add(proprietario);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void excluir(String email) {
        String sqlExcluir = "DELETE FROM proprietario WHERE email=?";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlExcluir);

            stmt.setString(1, email);

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}