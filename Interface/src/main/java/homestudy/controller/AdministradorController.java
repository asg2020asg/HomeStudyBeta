package homestudy.controller;

import homestudy.model.Administrador;
import homestudy.model.Usuario;

public class AdministradorController {

    public boolean exclusao(Administrador administrador, Usuario usuarioAlvo) {
        if (administrador == null || usuarioAlvo == null) {
            return false;
        }
        if (administrador.getEmail().equalsIgnoreCase(usuarioAlvo.getEmail())) {
            return false;
        }
        administrador.excluirUsuario(usuarioAlvo);
        return true;
    }

    public boolean bloqueio(Administrador administrador, Usuario usuarioAlvo) {
        if (administrador == null || usuarioAlvo == null) {
            return false;
        }
        if (administrador.getEmail().equalsIgnoreCase(usuarioAlvo.getEmail())) {
            return false;
        }
        administrador.bloquearUsuario(usuarioAlvo);
        return true;
    }
    public boolean suporte(Administrador administrador,String motivo) {
        if (administrador == null || motivo == null || motivo.trim().isEmpty()) {
            return false;
        }
        administrador.solicitarSuporte(motivo);
        return true;
    }
}