package homestudy.dao;

import homestudy.model.Aluno;
import homestudy.model.Usuario; // Importar Usuario para usar o UsuarioDao
import homestudy.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private UsuarioDao usuarioDao; // Adiciona uma instância de UsuarioDao

    public AlunoDao() {
        this.usuarioDao = new UsuarioDao(); // Inicializa UsuarioDao
    }

    // O construtor com Connection não é mais necessário se usarmos Conexao.getConnection()
    // public AlunoDao(Connection conexao) {
    //     this.conexao = conexao;
    // }

    public void inserir(Aluno aluno) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Inserir dados do usuário na tabela 'usuario'
            int usuarioId = usuarioDao.inserir(aluno); // O método inserir do UsuarioDao agora retorna o ID gerado
            aluno.setId(usuarioId); // Define o ID gerado no objeto Aluno

            // 2. Inserir dados específicos do aluno na tabela 'aluno'
            String sqlAluno = "INSERT INTO aluno (usuario_id, curso, periodo) VALUES (?, ?, ?)";
            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                stmtAluno.setInt(1, aluno.getId());
                stmtAluno.setString(2, aluno.getCurso());
                stmtAluno.setString(3, aluno.getPeriodo());
                stmtAluno.executeUpdate();
            }

            conn.commit(); // Confirma a transação
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir aluno", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o auto-commit
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }


    public Aluno buscarPorEmail(String email) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            // 1. Buscar o usuário pelo email
            Usuario usuario = usuarioDao.buscarPorEmail(email);

            if (usuario == null) {
                return null; // Usuário não encontrado
            }

            // 2. Buscar os dados específicos do aluno usando o ID do usuário
            String sqlAluno = "SELECT curso, periodo FROM aluno WHERE usuario_id = ?";
            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                stmtAluno.setInt(1, usuario.getId());
                try (ResultSet rsAluno = stmtAluno.executeQuery()) {
                    if (rsAluno.next()) {
                        return new Aluno(
                                usuario.getNome(),
                                usuario.getEmail(),
                                usuario.getTelefone(),
                                usuario.getSenha(),
                                usuario.getDataNascimento(),
                                rsAluno.getString("curso"),
                                rsAluno.getString("periodo")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aluno por e-mail", e);
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


    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT u.id, u.nome, u.email, u.telefone, u.senha, u.data_nascimento, a.curso, a.periodo " +
                     "FROM usuario u JOIN aluno a ON u.id = a.usuario_id";
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            rs.getDate("data_nascimento"),
                            rs.getString("curso"),
                            rs.getString("periodo")
                    );
                    aluno.setId(rs.getInt("id"));
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar alunos", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
        return alunos;
    }


    public void atualizar(Aluno aluno) {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Atualizar dados do usuário na tabela 'usuario'
            usuarioDao.atualizar(aluno); // O método atualizar do UsuarioDao já usa o ID do usuário

            // 2. Atualizar dados específicos do aluno na tabela 'aluno'
            String sqlAluno = "UPDATE aluno SET curso=?, periodo=? WHERE usuario_id=?";
            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlAluno)) {
                stmtAluno.setString(1, aluno.getCurso());
                stmtAluno.setString(2, aluno.getPeriodo());
                stmtAluno.setInt(3, aluno.getId());
                stmtAluno.executeUpdate();
            }

            conn.commit(); // Confirma a transação
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aluno", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o auto-commit
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
            conn.setAutoCommit(false); // Inicia transação

            // 1. Buscar o usuário pelo email para obter o ID
            Usuario usuario = usuarioDao.buscarPorEmail(email);
            if (usuario == null) {
                throw new RuntimeException("Aluno com e-mail " + email + " não encontrado para exclusão.");
            }

            // 2. Excluir o usuário (a exclusão em 'aluno' será em cascata devido ao ON DELETE CASCADE)
            usuarioDao.excluir(usuario.getId());

            conn.commit(); // Confirma a transação
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Desfaz a transação em caso de erro
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir aluno", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o auto-commit
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}
