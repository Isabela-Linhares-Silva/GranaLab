package com.example.granalab.security;

import com.example.granalab.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço de geração e validação de tokens de autenticação (stateless).
 * Conforme SPEC-001 Seção 10 - Pós-condições de login.
 */
@Service
public class TokenService {

    // Map em memória para associação token -> id do usuário (desenvolvimento)
    private final Map<String, Integer> tokens = new ConcurrentHashMap<>();
    // Map em memória para tokens de redefinição de senha (email -> token)
    private final Map<String, String> tokensRedefinicao = new ConcurrentHashMap<>();

    private final UsuarioRepository usuarioRepository;

    public TokenService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Gera um token de autenticação para o usuário.
     *
     * @param idUser identificador do usuário
     * @return token gerado
     */
    public String gerarToken(Integer idUser) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, idUser);
        return token;
    }

    /**
     * Recupera o identificador do usuário a partir do token.
     *
     * @param token token de autenticação
     * @return id do usuário, ou null se token inválido
     */
    public Integer validarToken(String token) {
        return tokens.get(token);
    }

    /**
     * Gera um token de redefinição de senha associado ao e-mail (RN12).
     *
     * @param email e-mail do usuário
     * @return token gerado
     */
    public String gerarTokenRedefinicaoSenha(String email) {
        if (!usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail não cadastrado (EX010)");
        }
        String token = UUID.randomUUID().toString();
        tokensRedefinicao.put(email, token);
        return token;
    }

    /**
     * Valida um token de redefinição de senha.
     *
     * @param email  e-mail do usuário
     * @param token  token a ser validado
     * @return true se o token é válido para o e-mail
     */
    public boolean validarTokenRedefinicaoSenha(String email, String token) {
        String tokenArmazenado = tokensRedefinicao.get(email);
        return tokenArmazenado != null && tokenArmazenado.equals(token);
    }

    /**
     * Remove o token de redefinição após uso (invalidar).
     *
     * @param email e-mail do usuário
     */
    public void invalidarTokenRedefinicaoSenha(String email) {
        tokensRedefinicao.remove(email);
    }
}