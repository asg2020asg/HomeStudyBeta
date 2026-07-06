package homestudy.controller;

import homestudy.model.Administrador;
import homestudy.model.Aluno;
import homestudy.model.Proprietario;
import homestudy.dao.AdministradorDao;
import homestudy.dao.AlunoDao;
import homestudy.dao.ProprietarioDao;
import homestudy.dao.UsuarioDao; // Importar UsuarioDao
import homestudy.model.Usuario;

import java.util.List;
import java.util.Date;

public class UsuarioController {
    //atributos
    private AdministradorDao administradorDao;
    private ProprietarioDao proprietarioDao;
    private AlunoDao alunoDao;
    private UsuarioDao usuarioDao; // Adicionado UsuarioDao
    //construtor
    public UsuarioController(){
        this.administradorDao = new AdministradorDao();
        this.proprietarioDao = new ProprietarioDao();
        this.alunoDao = new AlunoDao();
        this.usuarioDao = new UsuarioDao(); // Inicializa UsuarioDao
    }
    //metodos
    //login,condição para quem quer entrar
    // ( 3 for if para a checagem do email, para descobrir quem é quem)
    public Usuario Login(String email,String senha) { // Alterado o tipo de retorno para Usuario
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            return null; // Indica campos vazios, será tratado no LoginViewController
        }
        //em adm
        for (Administrador adm : administradorDao.listarTodos()) {
            if (adm.getEmail().equalsIgnoreCase(email) && adm.getSenha().equals(senha)) {
                return adm; // Retorna o objeto Administrador
            }
        }//em proprietario
        for (Proprietario prop : proprietarioDao.listarTodos()) {
            if (prop.getEmail().equalsIgnoreCase(email) && prop.getSenha().equals(senha)) {
                return prop; // Retorna o objeto Proprietario
            }
        }//em aluno
        for (Aluno aluno : alunoDao.listarTodos()) {
            if (aluno.getEmail().equalsIgnoreCase(email) && aluno.getSenha().equals(senha)) {
                return aluno; // Retorna o objeto Aluno
            }
        }
        return null; // Indica acesso inválido, será tratado no LoginViewController
    }
    public boolean modificarPerfil(Usuario usuarioAtual, String novoNome, String novoEmail, String novoTelefone, String novaSenha, Date novaData) {

        // Condições de validação
        if (novoNome == null || novoNome.trim().isEmpty() || novoEmail == null || novoEmail.trim().isEmpty()) {
            return false;
        }
        if (!novoEmail.contains("@")) {
            return false;
        }
        // direto da classe usuario,
        // public void editarDadosPessoa(String nome, String email, String telefone, String senha, Date dataNascimento)
        usuarioAtual.editarDadosPessoa(novoNome, novoEmail, novoTelefone, novaSenha, novaData);
        return true;
    }
    public boolean excluirPerfil(Usuario usuarioAtual){
        if(usuarioAtual == null){
            return false;
        }
        usuarioAtual.excluirDadosPessoa();
        return true;
    }

    /**
     * Verifica se o email e a data de nascimento correspondem a um usuário existente.
     * @param email O email do usuário.
     * @param dataNascimento A data de nascimento do usuário.
     * @return O objeto Usuario se a correspondência for encontrada, caso contrário, null.
     */
    public Usuario verificarEmailDataNascimento(String email, Date dataNascimento) {
        // Busca o usuário pelo email na tabela geral de usuários
        Usuario usuario = usuarioDao.buscarPorEmail(email);

        if (usuario != null && usuario.getDataNascimento().equals(dataNascimento)) {
            // Se encontrou o usuário e a data de nascimento coincide,
            // precisamos retornar o tipo específico de usuário (Administrador, Proprietario, Aluno)
            // para que o fluxo da aplicação possa continuar corretamente.
            // Isso é importante para o MainViewController, por exemplo, que precisa saber o tipo de usuário logado.

            // Verifica em Administradores
            for (Administrador adm : administradorDao.listarTodos()) {
                if (adm.getId() == usuario.getId()) {
                    return adm;
                }
            }
            // Verifica em Proprietários
            for (Proprietario prop : proprietarioDao.listarTodos()) {
                if (prop.getId() == usuario.getId()) {
                    return prop;
                }
            }
            // Verifica em Alunos
            for (Aluno aluno : alunoDao.listarTodos()) {
                if (aluno.getId() == usuario.getId()) {
                    return aluno;
                }
            }
        }
        return null; // Nenhuma correspondência encontrada ou data de nascimento incorreta
    }

    /**
     * Atualiza a senha de um usuário.
     * @param email O email do usuário cuja senha será atualizada.
     * @param novaSenha A nova senha.
     * @return true se a senha foi atualizada com sucesso, false caso contrário.
     */
    public boolean atualizarSenha(String email, String novaSenha) {
        // Primeiro, encontre o usuário pelo email usando UsuarioDao
        Usuario usuario = usuarioDao.buscarPorEmail(email);

        if (usuario != null) {
            // Atualiza a senha diretamente no UsuarioDao
            return usuarioDao.atualizarSenha(usuario.getId(), novaSenha);
        }
        return false; // Usuário não encontrado
    }
}