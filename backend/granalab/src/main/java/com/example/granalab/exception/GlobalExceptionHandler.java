package com.example.granalab.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Tratamento padronizado de exceções para a API REST.
 * Retorna respostas JSON com códigos HTTP adequados conforme SPEC-001 Seção 9.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroPerfilException.class)
    public ResponseEntity<Map<String, Object>> handleErroPerfil(ErroPerfilException ex) {
        return respostaErro(ex.getStatus(), ex.getCodigo(), ex.getMessage());
    }

    /**
     * Trata exceções de negócio lançadas com IllegalArgumentException (EX001 a EX011).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        String message = ex.getMessage();
        return respostaErro(HttpStatus.BAD_REQUEST, "DADOS_INVALIDOS",
                message != null ? message : "Erro de validação");
    }

    /**
     * Trata falhas de validação do Bean Validation (@NotBlank, @Email, @Size, etc.).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> campos = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            campos.put(nomeCampo, mensagem);
        });

        body.put("erro", "Campos inválidos");
        body.put("codigo", "DADOS_INVALIDOS");
        body.put("campos", campos);
        body.put("sucesso", false);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBodyInvalido(HttpMessageNotReadableException ex) {
        return respostaErro(HttpStatus.BAD_REQUEST, "DADOS_INVALIDOS",
                "Corpo da requisição ausente ou em formato inválido");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConflitoPersistencia(DataIntegrityViolationException ex) {
        return respostaErro(HttpStatus.CONFLICT, "CONFLITO_PERSISTENCIA",
                "Os dados enviados conflitam com uma restrição de integridade");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleFalhaPersistencia(DataAccessException ex) {
        return respostaErro(HttpStatus.INTERNAL_SERVER_ERROR, "FALHA_PERSISTENCIA",
                "Não foi possível salvar as alterações");
    }

    /**
     * Trata exceções inesperadas (erro interno do servidor).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return respostaErro(HttpStatus.INTERNAL_SERVER_ERROR, "ERRO_INTERNO",
                "Erro interno do servidor");
    }

    private ResponseEntity<Map<String, Object>> respostaErro(HttpStatus status, String codigo, String mensagem) {
        Map<String, Object> body = new HashMap<>();
        body.put("erro", mensagem);
        body.put("codigo", codigo);
        body.put("sucesso", false);
        return ResponseEntity.status(status).body(body);
    }
}
