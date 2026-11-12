package com.example.granalab.service;

import com.example.granalab.dto.UsuarioResponse;
import com.example.granalab.model.Usuario;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de autenticação de usuários.
 * Verifica credenciais e retorna dados públicos do usuário autenticado.
 */
@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica um usuário por e-mail e senha.
     *
     * @param email  e-mail do usuário
     * @param senha  senha informada (texto puro)
     * @return resposta pública do usuário autenticado (sem senha_hash)
     * @throws IllegalArgumentException se e-mail não cadastrado (EX010) ou senha incorreta (EX011)
     */
    public UsuarioResponse autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("E-mail não cadastrado (EX010)");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(senha, usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Senha incorreta (EX011)");
        }

        return UsuarioResponse.from(usuario);
    }
}