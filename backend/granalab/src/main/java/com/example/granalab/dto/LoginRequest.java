package com.example.granalab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para autenticação (login).
 * Campos conforme SPEC-001 Seção 5.
 */
public record LoginRequest(

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ter um formato válido")
    @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 255, message = "Senha deve ter entre 8 e 255 caracteres")
    String senha
) {
}