package homestudy.controller;

import homestudy.model.Imovel;

public class ImovelController {
    public boolean editarImovel(Imovel imovelAtual,String novoNome, String novoEndereco,String novaInfo
    ,String novoValor){
        if (imovelAtual == null){
            return false;
        }
        if(novoNome == null || novoNome.trim().isEmpty() ||
                novoEndereco == null || novoEndereco.trim().isEmpty() ||
                novoValor ==null || novoValor.trim().isEmpty()) {
                return false;
        }
            imovelAtual.setNomeImovel(novoNome);
            imovelAtual.setEndereco(novoEndereco);
            imovelAtual.setInformacaoImovel(novaInfo);
            imovelAtual.setValorImovel(novoValor);

            return true;
    }

    public boolean excluirImovel(Imovel imovelAtual){
        if(imovelAtual == null) {
            return false;
        }
            imovelAtual.excluiDadosImovel();

            return true;
    }
}
