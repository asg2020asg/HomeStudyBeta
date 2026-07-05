package homestudy.dao;

import homestudy.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void inserir(Usuario usuario){
        String sql = "INSERT INTO usuario(nome,email,telefone,senha,dataNscimento) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,usuario.getNome());
            stmt.setString(2,usuario.getEmail());
            stmt.setString(3,usuario.getTelefone());
            stmt.setString(4,usuario.getSenha());
            stmt.setDate(5,new Date(usuario.getDataNascimento().getTime()));

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void atualizar(Usuario usuario){
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,usuario.getNome());
            stmt.setString(2,usuario.getEmail());
            stmt.setString(3,usuario.getTelefone());
            stmt.setString(4,usuario.getSenha());
            stmt.setDate(5,new Date(usuario.getDataNascimento().getTime()));
            stmt.setInt(6,usuario.getId());

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Usuario buscarPorid(int id) {
        String sql = "SELECT * FROM usuario WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getDate("DataNascimento"));

                usuario.setId(rs.getInt("id"));

                rs.close();
                stmt.close();
                conn.close();

                return usuario;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Usuario> listarTodos(){
        String sql= "SELECT * FROM usuario";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getDate("DataNascimento")
                );

            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void excluir(int id){
        String sql = "DELETE FROM usuario WHERE id= ?";
    try{
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    catch(SQLException e){
        e.printStackTrace();
    }
}
}