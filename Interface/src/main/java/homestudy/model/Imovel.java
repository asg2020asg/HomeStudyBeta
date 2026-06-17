package homestudy.model;

public class Imovel {
    //atributos
    private String nomeImovel;
    private String endereco;
    private String informacaoImovel;
    private String valorImovel;

    //construtor
    public Imovel(String nomeImovel, String endereco, String informacaoImovel, String valorImovel) {
        this.nomeImovel = nomeImovel;
        this.endereco = endereco;
        this.informacaoImovel = informacaoImovel;
        this.valorImovel = valorImovel;
    }

    public void setNomeImovel(String nomeImovel) {
        this.nomeImovel = nomeImovel;
    }

    String getNomeImovel() {
        return nomeImovel;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    String getEndereco() {
        return endereco;
    }

    public void setInformacaoImovel(String informacaoImovel) {
        this.informacaoImovel = informacaoImovel;
    }

    String getInformacaoImovel() {
        return informacaoImovel;
    }

    public void setValorImovel(String valorImovel) {
        this.valorImovel = valorImovel;
    }

    String getValorImovel() {
        return valorImovel;
    }

    //metodos
    public void exibirDadosDoImovel() {
        System.out.println(" Dados do Imóvel: \n");
        System.out.println("Nome: " + getNomeImovel());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Informação do Imóvel: " + getInformacaoImovel());
        System.out.println("Valor do Imóvel: " + getValorImovel());

    }

    public void editarDadosDoImovel(String nome, String endereco, String informacaoImovel, String valorImovel) {
        setNomeImovel(nomeImovel);
        setEndereco(endereco);
        setInformacaoImovel(informacaoImovel);
        setValorImovel(valorImovel);

    }

    public void excluiDadosDoImovel() {
        nomeImovel = null;
        endereco = null;
        informacaoImovel = null;
        valorImovel = null;
    }
}