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

    public String getNomeImovel() {
        return nomeImovel;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setInformacaoImovel(String informacaoImovel) {
        this.informacaoImovel = informacaoImovel;
    }

    public String getInformacaoImovel() {
        return informacaoImovel;
    }

    public void setValorImovel(String valorImovel) {
        this.valorImovel = valorImovel;
    }

    public String getValorImovel() {
        return valorImovel;
    }

    //metodos
    public void exibirDadosImovel() {
        System.out.println(" Dados do Imóvel: \n");
        System.out.println("Nome: " + getNomeImovel());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Informação do Imóvel: " + getInformacaoImovel());
        System.out.println("Valor do Imóvel: " + getValorImovel());

    }

    public void editarDadosImovel(String nome, String endereco, String informacaoImovel, String valorImovel) {
        setNomeImovel(nomeImovel);
        setEndereco(endereco);
        setInformacaoImovel(informacaoImovel);
        setValorImovel(valorImovel);

    }

    public void excluiDadosImovel() {
        nomeImovel = null;
        endereco = null;
        informacaoImovel = null;
        valorImovel = null;
    }
}