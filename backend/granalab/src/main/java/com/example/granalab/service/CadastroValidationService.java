package com.example.granalab.service;

import com.example.granalab.dto.CadastroAlunoRequest;
import com.example.granalab.dto.CadastroMentorRequest;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Serviço de validação de dados de cadastro conforme SPEC-001 Seção 6.
 * Valida regras de negócio que vão além das anotações de Bean Validation.
 */
@Service
public class CadastroValidationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    /**
     * Valida os dados de cadastro de Aluno.
     *
     * @param request dados de cadastro do aluno
     * @throws IllegalArgumentException se alguma regra de negócio for violada
     */
    public void validarCadastroAluno(CadastroAlunoRequest request) {
        validarCamposComuns(request);
        validarDataNascimento(request.dataNascimento());
        validarSenha(request.senha());
        validarConfirmacaoSenha(request.senha(), request.confirmaSenha());
    }

    /**
     * Valida os dados de cadastro de Mentor.
     *
     * @param request dados de cadastro do mentor
     * @throws IllegalArgumentException se alguma regra de negócio for violada
     */
    public void validarCadastroMentor(CadastroMentorRequest request) {
        validarCamposComuns(request);
        validarSenha(request.senha());
        validarConfirmacaoSenha(request.senha(), request.confirmaSenha());
    }

    private void validarCamposComuns(Object request) {
        // Validação de domínio de e-mail (RN02 parte 2)
        String email = extrairEmail(request);
        if (email != null && !dominioEmailValido(email)) {
            throw new IllegalArgumentException("Domínio de e-mail inválido (EX006)");
        }
    }

    private String extrairEmail(Object request) {
        if (request instanceof CadastroAlunoRequest a) return a.email();
        if (request instanceof CadastroMentorRequest m) return m.email();
        return null;
    }

    private boolean dominioEmailValido(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String dominio = email.substring(email.indexOf('@') + 1);
        return dominio.contains(".");
    }

    private void validarDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento != null && !dataNascimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser futura ou igual à data atual (EX007)");
        }
    }

    private void validarSenha(String senha) {
        if (senha != null && senha.contains(" ")) {
            throw new IllegalArgumentException("Senha não deve conter espaço em branco (EX009)");
        }
    }

    private void validarConfirmacaoSenha(String senha, String confirmaSenha) {
        if (senha == null || !senha.equals(confirmaSenha)) {
            throw new IllegalArgumentException("Senha e confirmação não correspondem (EX004)");
        }
    }
}