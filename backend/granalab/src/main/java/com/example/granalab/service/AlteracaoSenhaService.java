package com.example.granalab.service;

import com.example.granalab.dto.AlteracaoSenhaRequest;
import com.example.granalab.model.Usuario;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlteracaoSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AlteracaoSenhaService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void alterar(Integer idUser, AlteracaoSenhaRequest request) {
        validarPresenca(request);

        Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(() -> new IllegalStateException("Conta autenticada não encontrada"));

        if (!passwordEncoder.matches(request.senhaAtual(), usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
        validarNovaSenha(request.novaSenha());

        if (!request.novaSenha().equals(request.confirmacaoNovaSenha())) {
            throw new IllegalArgumentException("Nova senha e confirmação não correspondem");
        }
        if (passwordEncoder.matches(request.novaSenha(), usuario.getSenhaHash())) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da senha atual");
        }

        usuario.setSenhaHash(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.save(usuario);
    }

    private void validarPresenca(AlteracaoSenhaRequest request) {
        if (request == null
                || request.senhaAtual() == null || request.senhaAtual().isBlank()
                || request.novaSenha() == null || request.novaSenha().isBlank()
                || request.confirmacaoNovaSenha() == null || request.confirmacaoNovaSenha().isBlank()) {
            throw new IllegalArgumentException("Senha atual, nova senha e confirmação são obrigatórias");
        }
    }

    private void validarNovaSenha(String senha) {
        if (senha.length() < 8 || senha.length() > 255) {
            throw new IllegalArgumentException("A nova senha deve possuir entre 8 e 255 caracteres");
        }
        if (senha.contains(" ")) {
            throw new IllegalArgumentException("A nova senha não deve conter espaço em branco");
        }
    }
}
