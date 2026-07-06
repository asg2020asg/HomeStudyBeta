package homestudy.controller;

import homestudy.dao.AlunoDao;
import homestudy.dao.ImovelDao;
import homestudy.model.Aluno;
import homestudy.model.Imovel;


import java.util.Date;
import java.util.List;

public class AlunoController {

    private final AlunoDao alunoDao;
    private final ImovelDao imovelDao;

    public AlunoController() {

        this.alunoDao = new AlunoDao(); // CORRIGIDO: Não passa mais Conexao.getConnection()
        this.imovelDao = new ImovelDao();
    }


    public void cadastrarAluno(String nome, String email, String telefone, String senha, Date dataNascimento, String curso, String periodo) {


        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty() || curso == null || curso.trim().isEmpty()) {
            System.out.println("[ERRO] Nome, E-mail e Curso são obrigatórios!");
            return;
        }

        try {
            Aluno novoAluno = new Aluno(nome, email, telefone, senha, dataNascimento, curso, periodo);
            alunoDao.inserir(novoAluno);
            System.out.println("[SUCESSO] Aluno '" + nome + "' foi gravado no banco de dados!");
        } catch (RuntimeException e) {
            System.out.println("[ERRO CRÍTICO] Falha ao tentar salvar o aluno no MySQL: " + e.getMessage());
        }
    }

    public Aluno buscarAlunoPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("[ERRO] Informe um e-mail válido para a busca!");
            return null;
        }

        try {
            Aluno alunoEncontrado = alunoDao.buscarPorEmail(email);
            if (alunoEncontrado != null) {
                System.out.println("[SUCESSO] Aluno encontrado: " + alunoEncontrado.getNome());
                return alunoEncontrado;
            } else {
                System.out.println("[AVISO] Nenhum aluno encontrado com o e-mail: " + email);
                return null;
            }

        } catch (RuntimeException e) {
            System.out.println("[ERRO] Falha ao buscar aluno por e-mail: " + e.getMessage());
            return null;
        }
    }

    public List<Aluno> listarTodosAlunos() {
        try {
            return alunoDao.listarTodos();
        } catch (RuntimeException e) {
            System.out.println("[ERRO] Não foi possível listar os alunos: " + e.getMessage());
            return null;
        }
    }

    public void atualizarDadosAluno(Aluno aluno) {
        if (aluno == null || aluno.getEmail() == null) {
            System.out.println("[ERRO] Dados inválidos para atualização!");
            return;
        }

        try {
            alunoDao.atualizar(aluno);
            System.out.println("[SUCESSO] Dados do aluno '" + aluno.getNome() + "' atualizados com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("[ERRO] Falha ao atualizar aluno: " + e.getMessage());
        }
    }

    public void excluirAluno(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("[ERRO] Informe o e-mail da conta que deseja excluir!");
            return;
        }

        try {
            alunoDao.excluir(email);
            System.out.println("[SUCESSO] Comando de exclusão enviado para o e-mail: " + email);
        } catch (RuntimeException e) {
            System.out.println("[ERRO] Falha ao tentar excluir a conta: " + e.getMessage());
        }
    }

    public List<Imovel> buscarImoveisDisponiveis() {
        try {
            return imovelDao.listarTodos();
        } catch (RuntimeException e) { // Captura a RuntimeException lançada pelo DAO
            System.out.println("[ERRO] Não foi possível carregar a lista de imóveis: " + e.getMessage());
            return null;
        }
    }

    public void exibirImoveisNoConsole() {
        List<Imovel> imoveis = buscarImoveisDisponiveis();

        System.out.println("      IMÓVEIS DISPONÍVEIS PARA ALUNOS     ");

        if (imoveis == null || imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel cadastrado no momento.");
        } else {
            for (Imovel imovel : imoveis) {
                System.out.println("Imóvel: " + imovel.getNomeImovel() + " | Endereço: " + imovel.getEndereco() + " | Valor: R$ " + imovel.getValorImovel());
                System.out.println("--------------------");
            }
        }
        System.out.println("=======================\n");
    }
}
