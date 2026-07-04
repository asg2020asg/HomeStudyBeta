package homestudy.dao;

import homestudy.model.Imovel;
import java.util.ArrayList;
import java.util.List;

public class ImovelDao {

    private final List<Imovel> listaImoveis;

    public ImovelDao() {
        this.listaImoveis = new ArrayList<>();
    }

    public void cadastrar(Imovel imovel) {
        if (imovel != null) {
            listaImoveis.add(imovel);
            System.out.println("Imóvel '" + imovel.getNomeImovel() + "' cadastrado com sucesso!");
        } else {
            System.out.println("Erro: Não é possível cadastrar um imóvel vazio.");
        }
    }

    public List<Imovel> listarTodos() {
        return listaImoveis;
    }

    public Imovel buscarPorNome(String nome) {
        for (Imovel imovel : listaImoveis) {
            if (imovel.getNomeImovel().equalsIgnoreCase(nome)) {
                return imovel;
            }
        }
        return null;
    }

    public boolean atualizar(String nomeAntigo, Imovel imovelAtualizado) {
        for (int i = 0; i < listaImoveis.size(); i++) {
            if (listaImoveis.get(i).getNomeImovel().equalsIgnoreCase(nomeAntigo)) {
                listaImoveis.set(i, imovelAtualizado);
                System.out.println("Imóvel atualizado com sucesso!");
                return true;
            }
        }
        System.out.println("Erro: Imóvel com o nome '" + nomeAntigo + "' não encontrado para atualização.");
        return false;
    }

    public boolean remover(String nome) {
        Imovel imovelEncontrado = buscarPorNome(nome);
        if (imovelEncontrado != null) {
            listaImoveis.remove(imovelEncontrado);
            System.out.println("Imóvel removido com sucesso!");
            return true;
        }
        System.out.println("Erro: Imóvel não encontrado para exclusão.");
        return false;
    }
}