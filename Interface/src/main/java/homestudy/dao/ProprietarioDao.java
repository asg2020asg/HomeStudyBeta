package homestudy.dao;

import homestudy.util.Conexao;
import homestudy.model.Proprietario;
import homestudy.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;

public class ProprietarioDao {

    private UsuarioDao usuarioDao;

    public ProprietarioDao() {
        this.usuarioDao = new UsuarioDao();
    }

    public void inserir(Proprietario proprietario) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Inserir dados do usuário na tabela 'usuario'
            int usuarioId = usuarioDao.inserir(proprietario); // O método inserir do UsuarioDao retorna o ID gerado
            proprietario.setId(usuarioId); // Define o ID gerado no objeto Proprietario

            System.out.println("DEBUG ProprietarioDao: Proprietario.getId() após UsuarioDao.inserir: " + proprietario.getId()); // DEBUG

            // 2. Inserir dados específicos do proprietário na tabela 'proprietario'
            String sqlProprietario = "INSERT INTO proprietario (usuario_id) VALUES (?)";
            try (PreparedStatement stmtProprietario = conn.prepareStatement(sqlProprietario)) {
                stmtProprietario.setInt(1, proprietario.getId());
                System.out.println("DEBUG ProprietarioDao: Tentando inserir na tabela proprietario com usuario_id: " + proprietario.getId()); // DEBUG
                stmtProprietario.executeUpdate();
                System.out.println("DEBUG ProprietarioDao: Inserção na tabela proprietario bem-sucedida para usuario_id: " + proprietario.getId()); // DEBUG
            }

            conn.commit(); // Confirma a transação
            System.out.println("DEBUG ProprietarioDao: Transação de proprietário commitada."); // DEBUG
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                    System.err.println("DEBUG ProprietarioDao: Rollback da transação de proprietário."); // DEBUG
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir proprietário", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o auto-commit
                    conn.close();
                    System.out.println("DEBUG ProprietarioDao: Conexão de proprietário fechada."); // DEBUG
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public Proprietario buscarPorId(int id) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            // Primeiro, busca os dados de usuário
            Usuario usuario = usuarioDao.buscarPorId(id);

            if (usuario == null) {
                return null; // Usuário não encontrado
            }

            // Verifica se este usuário é um proprietário
            String sqlProprietario = "SELECT usuario_id FROM proprietario WHERE usuario_id = ?";
            try (PreparedStatement stmtProprietario = conn.prepareStatement(sqlProprietario)) {
                stmtProprietario.setInt(1, id);
                try (ResultSet rsProprietario = stmtProprietario.executeQuery()) {
                    if (rsProprietario.next()) {
                        // Se for um proprietário, cria o objeto Proprietario com os dados do usuário
                        Proprietario proprietario = new Proprietario(
                                usuario.getNome(),
                                usuario.getEmail(),
                                usuario.getTelefone(),
                                usuario.getSenha(),
                                usuario.getDataNascimento()
                        );
                        proprietario.setId(usuario.getId());
                        return proprietario;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar proprietário por ID", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
        return null; // Não é um proprietário ou não encontrado
    }


    public Proprietario buscarPorEmail(String email) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            Usuario usuario = usuarioDao.buscarPorEmail(email);

            if (usuario == null) {
                return null;
            }

            String sqlProprietario = "SELECT usuario_id FROM proprietario WHERE usuario_id = ?";
            try (PreparedStatement stmtProprietario = conn.prepareStatement(sqlProprietario)) {
                stmtProprietario.setInt(1, usuario.getId());
                try (ResultSet rsProprietario = stmtProprietario.executeQuery()) {
                    if (rsProprietario.next()) {
                        Proprietario proprietario = new Proprietario(
                                usuario.getNome(),
                                usuario.getEmail(),
                                usuario.getTelefone(),
                                usuario.getSenha(),
                                usuario.getDataNascimento()
                        );
                        proprietario.setId(usuario.getId());
                        return proprietario;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar proprietário por e-mail", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
        return null;
    }

    public List<Proprietario> listarTodos() {
        List<Proprietario> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.email, u.telefone, u.senha, u.data_nascimento " +
                "FROM usuario u JOIN proprietario p ON u.id = p.usuario_id";
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Proprietario proprietario = new Proprietario(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            rs.getDate("data_nascimento")
                    );
                    proprietario.setId(rs.getInt("id"));
                    lista.add(proprietario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar proprietários", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
        return lista;
    }

    public void atualizar(Proprietario proprietario) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);

            usuarioDao.atualizar(proprietario);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar proprietário", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public void excluir(String email) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);

            Usuario usuario = usuarioDao.buscarPorEmail(email);
            if (usuario == null) {
                throw new RuntimeException("Proprietário com e-mail " + email + " não encontrado para exclusão.");
            }

            usuarioDao.excluir(usuario.getId());

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir proprietário", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}