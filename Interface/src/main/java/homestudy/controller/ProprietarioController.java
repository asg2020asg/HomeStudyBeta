package homestudy.controller;

import homestudy.model.Proprietario;
import homestudy.model.Imovel;

public class ProprietarioController {
    public boolean novoImovel(Proprietario dono,Imovel novoImovel){
        if(dono == null || novoImovel ==null){
            return false;
    }
        if(novoImovel.getNomeImovel() == null || novoImovel.getNomeImovel().trim().isEmpty()){
            return false;
        }
        if(novoImovel.getEndereco()== null || novoImovel.getEndereco().trim().isEmpty()){
            return false;
        }
        dono.adicionarImovel(novoImovel);

        return true; //cadastrado
    }
    public boolean removerImovel(Proprietario dono, Imovel imovelAlvo){
    if(dono== null || imovelAlvo ==null){
        return false;
    }
    dono.removerImovel(imovelAlvo);
        return true;
    }
    public boolean exibirLista(Proprietario dono){
    if (dono == null){
        return false;
    }
    dono.exibirLista();
    return true;
    }
}
