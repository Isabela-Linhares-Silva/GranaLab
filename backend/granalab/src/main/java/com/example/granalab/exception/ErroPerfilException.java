package com.example.granalab.exception;

import org.springframework.http.HttpStatus;

/** Erro de domínio de perfil com código e status HTTP estáveis para a API. */
public class ErroPerfilException extends RuntimeException {

    private final String codigo;
    private final HttpStatus status;

    public ErroPerfilException(String codigo, String mensagem, HttpStatus status) {
        super(mensagem);
        this.codigo = codigo;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
