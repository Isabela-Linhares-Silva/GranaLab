package com.example.granalab.controller;

import com.example.granalab.dto.UsuarioResponse;
import com.example.granalab.dto.AtualizacaoPerfilRequest;
import com.example.granalab.dto.AlteracaoSenhaRequest;
import com.example.granalab.dto.PerfilResponse;
import com.example.granalab.security.UsuarioAutenticado;
import com.example.granalab.service.AlteracaoSenhaService;
import com.example.granalab.service.PerfilService;
import com.example.granalab.service.UsuarioService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;
    private final AlteracaoSenhaService alteracaoSenhaService;

    public UsuarioController(UsuarioService usuarioService, PerfilService perfilService,
                             AlteracaoSenhaService alteracaoSenhaService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
        this.alteracaoSenhaService = alteracaoSenhaService;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/me")
    public PerfilResponse consultarMeuPerfil(@AuthenticationPrincipal UsuarioAutenticado autenticado) {
        return perfilService.consultar(autenticado.idUser());
    }

    @PatchMapping("/me")
    public ResponseEntity<PerfilResponse> atualizarMeuPerfil(
            @AuthenticationPrincipal UsuarioAutenticado autenticado,
            @RequestBody AtualizacaoPerfilRequest request) {
        return ResponseEntity.ok(perfilService.atualizar(autenticado.idUser(), request));
    }

    @PostMapping("/me/senha")
    public ResponseEntity<Void> alterarMinhaSenha(
            @AuthenticationPrincipal UsuarioAutenticado autenticado,
            @RequestBody AlteracaoSenhaRequest request) {
        alteracaoSenhaService.alterar(autenticado.idUser(), request);
        return ResponseEntity.noContent().build();
    }
}
