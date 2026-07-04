package homestudy.dao;

import homestudy.model.Aluno;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private Connection conexao;

    public AlunoDao(Connection conexao) {
        this.conexao = conexao;
    }

    // CREATE
    public void inserir(Aluno aluno) {

        String sql =
                "INSERT INTO aluno " +
                        "(nome,email,telefone,senha,data_nascimento,curso,periodo) " +
                        "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getSenha());

            stmt.setDate(
                    5,
                    new Date(
                            aluno.getDataNascimento().getTime()
                    )
            );

            stmt.setString(6, aluno.getCurso());
            stmt.setString(7, aluno.getPeriodo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Aluno buscarPorEmail(String email) {

        String sql =
                "SELECT * FROM aluno WHERE email = ?";

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                return new Aluno(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getDate("data_nascimento"),
                        rs.getString("curso"),
                        rs.getString("periodo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Aluno> listarTodos() {

        List<Aluno> alunos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM aluno";

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                alunos.add(
                        new Aluno(
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("telefone"),
                                rs.getString("senha"),
                                rs.getDate("data_nascimento"),
                                rs.getString("curso"),
                                rs.getString("periodo")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }


    public void atualizar(Aluno aluno) {

        String sql =
                "UPDATE aluno " +
                        "SET nome=?, telefone=?, senha=?, curso=?, periodo=? " +
                        "WHERE email=?";

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getTelefone());
            stmt.setString(3, aluno.getSenha());
            stmt.setString(4, aluno.getCurso());
            stmt.setString(5, aluno.getPeriodo());
            stmt.setString(6, aluno.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void excluir(String email) {

        String sql =
                "DELETE FROM aluno WHERE email=?";

        try (PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, email);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}