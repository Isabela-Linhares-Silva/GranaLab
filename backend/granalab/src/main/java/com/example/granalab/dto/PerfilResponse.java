package com.example.granalab.dto;

import com.example.granalab.model.Genero;

import java.time.LocalDate;

/** Dados públicos do perfil autenticado, sem credenciais ou identificadores internos. */
public record PerfilResponse(
        String nome,
        String email,
        String urlAvatar,
        String tipoPerfil,
        LocalDate dataNascimento,
        Genero genero,
        String escolaAtua) {
}
