package homestudy.model;

import java.util.ArrayList;
import java.util.Date;

public class Proprietario extends Usuario {
    //atributos
    private ArrayList<Imovel> imoveis;

    //construtor
    public Proprietario(String nome, String email, String telefone, String senha, Date dataNascimento) {
        super(nome, email, telefone, senha, dataNascimento);
        imoveis = new ArrayList<>();
    }

    //metodos
    @Override
    public void exibirDados(){
        super.exibirDados();
    }
    public void adicionarImovel(Imovel imovel) {
        imoveis.add(imovel);
        System.out.println(" imóvel: " + imovel.getNomeImovel() + "de endereço:" +imovel.getEndereco()+", adicionado à lista.");
    }

    public void removerImovel(Imovel imovel) {
        imoveis.remove(imovel);
        System.out.println(" imovel: " + imovel.getNomeImovel() + "de endereço" +imovel.getEndereco()+", removido da lista.");
    }

    public void exibirLista() {
        System.out.println("Lista de imóveis: \n");
        for (Imovel imovel : imoveis) {
            System.out.println(" - " + imovel.getNomeImovel());
        }
    }
}