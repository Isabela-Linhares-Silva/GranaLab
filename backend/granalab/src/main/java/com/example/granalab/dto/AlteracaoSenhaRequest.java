package com.example.granalab.dto;

/** Contrato de entrada reservado para a operação de senha da tarefa T007. */
public record AlteracaoSenhaRequest(
        String senhaAtual,
        String novaSenha,
        String confirmacaoNovaSenha) {
}
