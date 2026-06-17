package homestudy.model;

import java.util.Date;

public class Usuario {
    //atributos
    protected String nome;
    protected String email;
    protected String telefone;
    protected String senha;
    protected Date dataNascimento;
    //construtor
    public Usuario(String nome,String email,String telefone,String senha,Date dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }
    public void setNome(String nome){ this.nome = nome;}
    String getNome(){
        return nome;
    }
    public void setEmail(String email){
        this.email = email;
    }
    String getEmail(){
        return email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    String getTelefone(){
        return telefone;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    String getSenha(){
        return senha;
    }
    public void setDataNascimento(Date dataNascimento){
        this.dataNascimento = dataNascimento;
    }
    Date getData(){
        return dataNascimento;
    }
    //metodos
    public void exibirDados(){
        System.out.println(" Dados do Usuário: ");
        System.out.println("Nome: "+nome);
        System.out.println("Email: "+email);
        System.out.println("Telefone: "+telefone);
        System.out.println("Senha: "+senha);
        System.out.println("Data de Nascimento: "+dataNascimento);
    }
    public void editarDadosDoUsuario(String nome, String email, String telefone, String senha, Date dataNascimento){
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
        setSenha(senha);
        setDataNascimento(dataNascimento);
    }
    public void excluirDadosDoUsuario(){
        nome = null;
        email = null;
        telefone = null;
        senha = null;
        dataNascimento = null;
    }
}
