package com.example.granalab.dto;

import com.example.granalab.model.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO de entrada para cadastro de usuário Aluno.
 * Campos conforme SPEC-001 Seção 5.
 */
public record CadastroAlunoRequest(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,

    @NotNull(message = "Data de nascimento é obrigatória")
    LocalDate dataNascimento,

    @NotNull(message = "Gênero é obrigatório")
    Genero genero,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ter um formato válido")
    @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 255, message = "Senha deve ter entre 8 e 255 caracteres")
    String senha,

    @NotBlank(message = "Confirmação de senha é obrigatória")
    String confirmaSenha
) {
}