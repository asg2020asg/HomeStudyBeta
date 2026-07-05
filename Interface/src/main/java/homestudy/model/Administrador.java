package homestudy.model;

import java.util.Date;

public class Administrador extends Usuario {
    //atributos
    private String login;

    //Construtor
    public Administrador(String nome, String email, String telefone, String senha, Date dataNascimento, String login) {
        super(nome, email, telefone, senha, dataNascimento);
        this.login =login;
    }

    public void setLogin(String login){
        this.login = login;}
    public String getLogin(){
        return login;}

    //metodos
    @Override
    public void exibirDados(){
        super.exibirDados();
    }
    public void usarSistema(){
        System.out.println("O Administrador(a): " +getNome()+" está utilizando o sistema.");
    }
    public void excluirUsuario(Usuario usuario){
        System.out.println("O Administrador(a): "+getNome()+" está excluindo o usuario: "+ usuario.getNome() +", Id: "+ usuario.getId()+ " do sistema.");
    }
    public void bloquearUsuario(Usuario usuario){
        System.out.println("O Administrador(a): "+getNome()+", esta bloqueando o usuário:"+ usuario.getNome()+", o mesmo nao poderá efetuar login.");
    };
    public void solicitarSuporte(String motivoProblema){
        System.out.println("-CANAL SUPORTE-: chamado pelo Administrador: "+this.getNome());
        System.out.println("Motivo do problema: "+ motivoProblema);
    }
}
