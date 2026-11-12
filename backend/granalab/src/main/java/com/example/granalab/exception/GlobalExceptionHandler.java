package com.example.granalab.exception;

import com.example.granalab.dto.UsuarioResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    /**
     * Trata exceções de negócio lançadas com IllegalArgumentException (EX001 a EX011).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        String message = ex.getMessage();
        Map<String, Object> body = new HashMap<>();
        body.put("erro", message != null ? message : "Erro de validação");
        body.put("sucesso", false);
        return ResponseEntity.badRequest().body(body);
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
        body.put("campos", campos);
        body.put("sucesso", false);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Trata exceções inesperadas (erro interno do servidor).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("erro", "Erro interno do servidor");
        body.put("sucesso", false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}