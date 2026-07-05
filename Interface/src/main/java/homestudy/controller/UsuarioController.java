package homestudy.controller;

import homestudy.model.Administrador;
import homestudy.model.Aluno;
import homestudy.model.Proprietario;
import homestudy.dao.AdministradorDao;
import homestudy.dao.AlunoDao;
import homestudy.dao.ProprietarioDao;
import homestudy.model.Usuario;

import java.util.List;
import java.util.Date;

public class UsuarioController {
    //atributos
    private AdministradorDao administradorDao;
    private ProprietarioDao proprietarioDao;
    private AlunoDao alunoDao;
    //construtor
    public UsuarioController(){
        this.administradorDao = new AdministradorDao();
        this.proprietarioDao = new ProprietarioDao();
        this.alunoDao = new AlunoDao();
    }
    //metodos
    //login,condição para quem quer entrar
    // ( 3 for if para a checagem do email, para descobrir quem é quem)
    public String Login(String email,String senha) {
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            return " espaço vazio";
        } //em adm
        for (Administrador adm : administradorDao.listarTodos()) {
            if (adm.getEmail().equalsIgnoreCase(email) && adm.getSenha().equals(senha)) {
                return " Administrador";
            }
        }//em proprietario
        for (Proprietario prop : proprietarioDao.listarTodos()) {
            if (prop.getEmail().equalsIgnoreCase(email) && prop.getSenha().equals(senha)) {
                return "Proprietario";
            }
        }//em aluno
        for (Aluno aluno : alunoDao.listarTodos()) {
            if (aluno.getEmail().equalsIgnoreCase(email) && aluno.getSenha().equals(senha)) {
                return "Aluno";
            }
        }
        return "acesso invalido";
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
}
