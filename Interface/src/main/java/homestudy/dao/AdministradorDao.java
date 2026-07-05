package homestudy.dao;

import homestudy.util.Conexao;
import homestudy.model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDao {

    public void inserir(Administrador admin) {
        String sqlInserir = "INSERT INTO administrador(email, login) VALUES(? , ?)";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInserir);

            stmt.setString(1, admin.getEmail());
            stmt.setString(2, admin.getLogin());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Administrador admin) {
        String sqlAtualizar = "UPDATE administrador SET login = ? WHERE email = ?";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlAtualizar);

            stmt.setString(1, admin.getLogin());
            stmt.setString(2, admin.getEmail());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Administrador> listarTodos() {
        String sqlListar = "SELECT * FROM administrador";
        List<Administrador> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlListar);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Administrador admin = new Administrador(
                        0,
                        "",
                        rs.getString("email"),
                        "",
                        "",
                        null,
                        rs.getString("login")
                );
                lista.add(admin);
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
        String sqlExcluir = "DELETE FROM administrador WHERE email = ?";
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