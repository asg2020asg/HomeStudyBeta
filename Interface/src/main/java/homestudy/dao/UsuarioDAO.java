package homestudy.dao;

import homestudy.model.Pessoa;
import homestudy.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Pessoa usuario){
        String sql = "INSERT INTO usuario(nome,email,telefone,senha,dataNscimento) VALUES(?,?,?,?,?)";
        try{
            Connection conn = Conexao.getConnection();
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
    public void atualizar(Pessoa usuario){
        String sql = "UPDATE usuario SET nome=?, email=?, telefone=?, senha=?, dataNascimento=? WHERE id=?";
        try{
            Connection conn = Conexao.getConnection();
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
    public Pessoa buscarPorid(int id) {
        String sql = "SELECT * FROM usuario WHERE id=?";
        try {
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pessoa usuario = new Pessoa(rs.getString("nome"),
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
    public List<Pessoa> listarTodos(){
        String sql= "SELECT * FROM usuario";
        List<Pessoa> usuarios = new ArrayList<>(); // Changed variable name to 'usuarios' for clarity
        try{
            Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Pessoa usuario = new Pessoa(rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getDate("DataNascimento")
                );

                usuario.setId(rs.getInt("id"));
                usuarios.add(usuario);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return usuarios;
    }
    public void excluir(int id){
        String sql = "DELETE FROM usuario WHERE id= ?";
    try{
        Connection conn = Conexao.getConnection();
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