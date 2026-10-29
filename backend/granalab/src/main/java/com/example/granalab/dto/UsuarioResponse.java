package com.example.granalab.dto;

import com.example.granalab.model.Usuario;

/** Resposta publica de Usuario. NAO contem senha_hash. */
public record UsuarioResponse(
        Integer idUser,
        String nome,
        String email,
        String urlAvatar,
        String tipoPerfil) {

    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(
                u.getIdUser(),
                u.getNome(),
                u.getEmail(),
                u.getUrlAvatar(),
                u.getTipoPerfil() == null ? null : u.getTipoPerfil().getValor());
    }
}