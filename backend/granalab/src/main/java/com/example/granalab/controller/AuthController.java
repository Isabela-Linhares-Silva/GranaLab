package com.example.granalab.controller;

import com.example.granalab.dto.CadastroAlunoRequest;
import com.example.granalab.dto.CadastroMentorRequest;
import com.example.granalab.dto.LoginRequest;
import com.example.granalab.dto.UsuarioResponse;
import com.example.granalab.service.AutenticacaoService;
import com.example.granalab.service.CadastroAlunoService;
import com.example.granalab.service.CadastroMentorService;
import com.example.granalab.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller de autenticação e cadastro.
 * Endpoints públicos conforme SPEC-001 Seção 12.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CadastroAlunoService cadastroAlunoService;
    private final CadastroMentorService cadastroMentorService;
    private final AutenticacaoService autenticacaoService;
    private final TokenService tokenService;

    public AuthController(
            CadastroAlunoService cadastroAlunoService,
            CadastroMentorService cadastroMentorService,
            AutenticacaoService autenticacaoService,
            TokenService tokenService) {
        this.cadastroAlunoService = cadastroAlunoService;
        this.cadastroMentorService = cadastroMentorService;
        this.autenticacaoService = autenticacaoService;
        this.tokenService = tokenService;
    }

    /**
     * Cadastra um novo usuário do tipo Aluno.
     *
     * @param request dados de cadastro de Aluno
     * @return resposta de sucesso com dados do usuário cadastrado
     */
    @PostMapping("/cadastro/aluno")
    public ResponseEntity<UsuarioResponse> cadastrarAluno(@RequestBody CadastroAlunoRequest request) {
        UsuarioResponse response = cadastroAlunoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cadastra um novo usuário do tipo Mentor.
     *
     * @param request dados de cadastro de Mentor
     * @return resposta de sucesso com dados do usuário cadastrado
     */
    @PostMapping("/cadastro/mentor")
    public ResponseEntity<UsuarioResponse> cadastrarMentor(@RequestBody CadastroMentorRequest request) {
        UsuarioResponse response = cadastroMentorService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Autentica um usuário por e-mail e senha.
     *
     * @param request dados de login (e-mail e senha)
     * @return token de autenticação e dados do usuário
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        UsuarioResponse usuario = autenticacaoService.autenticar(request.email(), request.senha());
        String token = tokenService.gerarToken(usuario.idUser());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuario);

        return ResponseEntity.ok(response);
    }

    /**
     * Solicita redefinição de senha enviando token por e-mail.
     *
     * @param email e-mail do usuário
     * @return mensagem de confirmação
     */
    @PostMapping("/redefinir-senha")
    public ResponseEntity<Map<String, String>> redefinirSenha(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        tokenService.gerarTokenRedefinicaoSenha(email);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Token de redefinição enviado por e-mail");

        return ResponseEntity.ok(response);
    }
}