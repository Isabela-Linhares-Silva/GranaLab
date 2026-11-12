package com.example.granalab.service;

import com.example.granalab.model.Usuario;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de verificação de unicidade de e-mail (RN04).
 * Impede a criação de contas com e-mail já cadastrado.
 */
@Service
public class CadastroUnicidadeService {

    private final UsuarioRepository usuarioRepository;

    public CadastroUnicidadeService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Verifica se o e-mail já está cadastrado no sistema.
     *
     * @param email e-mail a ser verificado
     * @throws IllegalArgumentException se o e-mail já estiver em uso (EX003)
     */
    public void verificarEmailNaoCadastrado(String email) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado (EX003)");
        }
    }
}