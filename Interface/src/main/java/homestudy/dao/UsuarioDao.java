package homestudy.dao;

import homestudy.util.Conexao;
import homestudy.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    //metodos
    public int inserir(Usuario usuario){ // Alterado para retornar int (o ID gerado)
        String sql = "INSERT INTO usuario(nome,email,telefone,senha,data_nascimento) VALUES(?,?,?,?,?)"; // Corrigido data_nascimento
        int generatedId = -1;
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Adicionado RETURN_GENERATED_KEYS

            stmt.setString(1,usuario.getNome());
            stmt.setString(2,usuario.getEmail());
            stmt.setString(3,usuario.getTelefone());
            stmt.setString(4,usuario.getSenha());
            stmt.setDate(5,new Date(usuario.getDataNascimento().getTime()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    System.out.println("DEBUG UsuarioDao: ID gerado para o usuário " + usuario.getEmail() + ": " + generatedId); // DEBUG
                } else {
                    System.out.println("DEBUG UsuarioDao: Nenhum ID gerado para o usuário " + usuario.getEmail()); // DEBUG
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir usuário", e); // Lançar exceção para tratamento superior
        }
        return generatedId;
    }

    public void atualizar(Usuario usuario){
        String sql = "UPDATE usuario SET nome=?, email=?, telefone=?, senha=?, data_nascimento=? WHERE id=?"; // Corrigido data_nascimento
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,usuario.getNome());
            stmt.setString(2,usuario.getEmail());
            stmt.setString(3,usuario.getTelefone());
            stmt.setString(4,usuario.getSenha());
            stmt.setDate(5,new Date(usuario.getDataNascimento().getTime()));
            stmt.setInt(6,usuario.getId());

            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    /**
     * Atualiza apenas a senha de um usuário no banco de dados.
     * @param userId O ID do usuário a ser atualizado.
     * @param novaSenha A nova senha a ser definida.
     * @return true se a senha foi atualizada com sucesso, false caso contrário.
     */
    public boolean atualizarSenha(int userId, String novaSenha) {
        String sql = "UPDATE usuario SET senha=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar senha do usuário com ID " + userId, e);
        }
    }


    public Usuario buscarPorId(int id) { // Corrigido nome do método para buscarPorId
        String sql = "SELECT * FROM usuario WHERE id=?";
        Usuario usuario = null;
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            rs.getDate("data_nascimento")); // Corrigido data_nascimento

                    usuario.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) { // Novo método para buscar por email
        String sql = "SELECT * FROM usuario WHERE email=?";
        Usuario usuario = null;
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            rs.getDate("data_nascimento")); // Corrigido data_nascimento

                    usuario.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }
        return usuario;
    }

    public List<Usuario> listarTodos(){
        String sql= "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>(); // Renomeado para 'usuarios' para clareza
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){
                Usuario usuario = new Usuario(rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getDate("data_nascimento") // Corrigido data_nascimento
                );
                usuario.setId(rs.getInt("id"));
                usuarios.add(usuario);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

    public void excluir(int id){
        String sql = "DELETE FROM usuario WHERE id= ?";
        try(Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }
}