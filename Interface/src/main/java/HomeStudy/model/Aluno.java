package homestudy.model;

import java.util.Date;
import java.util.List;

public class Aluno extends Usuario {
    //atributos
    private String curso;
    private String periodo;
    //construtor
    public Aluno(String nome, String email, String telefone,String senha, Date dataNascimento,String curso,String periodo){
        super(nome , email, telefone, senha, dataNascimento);
        this.curso = curso;
        this.periodo = periodo;
    }
    public void setCurso(String curso){this.curso = curso;}
    String getCurso(){return curso;}
    public void setPeriodo(String periodo){this.periodo = periodo;}
    String getPeriodo(){return periodo;}
    //metodos
    @Override
    public void exibirDados(){
        super.exibirDados();
        System.out.println("Curso: "+curso+"Semestre: "+periodo);
    }
    public List<Imovel> buscarImoveis();
    public List<Aluno> buscarParceiros();
}
