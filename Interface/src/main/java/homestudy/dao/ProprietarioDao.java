package homestudy.dao;

import homestudy.model.Proprietario;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDao {

    // Lista que simula a nossa tabela de proprietários no banco de dados em memória
    private final List<Proprietario> listaProprietarios;

    // Construtor
    public ProprietarioDao() {
        this.listaProprietarios = new ArrayList<>();
    }

    public void cadastrar(Proprietario proprietario) {
        if (proprietario != null) {
            // Verifica se já existe alguém cadastrado com o mesmo e-mail para evitar duplicatas
            if (buscarPorEmail(proprietario.getEmail()) != null) {
                System.out.println("Erro: Já existe um proprietário cadastrado com o e-mail " + proprietario.getEmail());
                return;
            }
            listaProprietarios.add(proprietario);
            System.out.println("Proprietário(a) '" + proprietario.getNome() + "' cadastrado(a) com sucesso!");
        } else {
            System.out.println("Erro: Não é possível cadastrar um proprietário vazio.");
        }
    }

    public List<Proprietario> listarTodos() {
        return listaProprietarios;
    }

    public Proprietario buscarPorEmail(String email) {
        for (Proprietario p : listaProprietarios) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return p;
            }
        }
        return null; // Retorna null se não encontrar
    }

    public boolean atualizar(String emailAntigo, Proprietario proprietarioAtualizado) {
        for (int i = 0; i < listaProprietarios.size(); i++) {
            if (listaProprietarios.get(i).getEmail().equalsIgnoreCase(emailAntigo)) {
                listaProprietarios.set(i, proprietarioAtualizado);
                System.out.println("Dados do(a) proprietário(a) '" + proprietarioAtualizado.getNome() + "' atualizados com sucesso!");
                return true;
            }
        }
        System.out.println("Erro: Proprietário(a) com o e-mail '" + emailAntigo + "' não encontrado(a) para atualização.");
        return false;
    }

    public boolean remover(String email) {
        Proprietario p = buscarPorEmail(email);
        if (p != null) {
            listaProprietarios.remove(p);
            System.out.println("Proprietário(a) removido(a) com sucesso!");
            return true;
        }
        System.out.println("Erro: Proprietário(a) não encontrado(a) para exclusão.");
        return false;
    }
}