package homestudy.model;

public class Imovel {
    //atributos
    private int id; // Adicionado para o ID do imóvel no banco de dados
    private int proprietarioId; // Adicionado para a chave estrangeira do proprietário
    private String nomeImovel;
    private String endereco;
    private String informacaoImovel;
    private String valorImovel;
    private String tipoImovel; // NOVO CAMPO: Tipo do imóvel

    // Construtor original (útil para criar um Imovel antes de ter um ID ou proprietarioId)
    public Imovel(String nomeImovel, String endereco, String informacaoImovel, String valorImovel, String tipoImovel) {
        this.nomeImovel = nomeImovel;
        this.endereco = endereco;
        this.informacaoImovel = informacaoImovel;
        this.valorImovel = valorImovel;
        this.tipoImovel = tipoImovel; // Inicializa o novo campo
        this.id = 0; // Valor padrão, será definido após inserção no DB
        this.proprietarioId = 0; // Valor padrão, será definido ao associar
    }

    // Novo construtor para quando o Imovel já tem um ID e proprietarioId (ex: ao buscar do DB)
    public Imovel(int id, int proprietarioId, String nomeImovel, String endereco, String informacaoImovel, String valorImovel, String tipoImovel) {
        this.id = id;
        this.proprietarioId = proprietarioId;
        this.nomeImovel = nomeImovel;
        this.endereco = endereco;
        this.informacaoImovel = informacaoImovel;
        this.valorImovel = valorImovel;
        this.tipoImovel = tipoImovel; // Inicializa o novo campo
    }

    // Getters e Setters para os novos campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProprietarioId() {
        return proprietarioId;
    }

    public void setProprietarioId(int proprietarioId) {
        this.proprietarioId = proprietarioId;
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

    // Getter e Setter para tipoImovel
    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    //metodos
    public void exibirDadosImovel() {
        System.out.println(" Dados do Imóvel: \n");
        System.out.println("ID: " + getId());
        System.out.println("ID Proprietário: " + getProprietarioId());
        System.out.println("Nome: " + getNomeImovel());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Informação do Imóvel: " + getInformacaoImovel());
        System.out.println("Valor do Imóvel: " + getValorImovel());
        System.out.println("Tipo do Imóvel: " + getTipoImovel()); // Exibe o novo campo
    }

    public void editarDadosImovel(String nome, String endereco, String informacaoImovel, String valorImovel, String tipoImovel) {
        this.nomeImovel = nome;
        this.endereco = endereco;
        this.informacaoImovel = informacaoImovel;
        this.valorImovel = valorImovel;
        this.tipoImovel = tipoImovel; // Permite editar o novo campo
    }

    public void excluiDadosImovel() {
        this.id = 0; // Resetar ID
        this.proprietarioId = 0; // Resetar ID do proprietário
        this.nomeImovel = null;
        this.endereco = null;
        this.informacaoImovel = null;
        this.valorImovel = null;
        this.tipoImovel = null; // Limpa o novo campo
    }
}