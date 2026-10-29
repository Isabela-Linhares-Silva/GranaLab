package com.example.granalab.model;

/** Espelha o ENUM('Mentor','Aluno') da coluna usuario.tipo_perfil. */
public enum TipoPerfil {
    MENTOR("Mentor"),
    ALUNO("Aluno");

    private final String valor;

    TipoPerfil(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoPerfil fromValor(String valor) {
        for (TipoPerfil t : values()) {
            if (t.valor.equals(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("tipo_perfil desconhecido: " + valor);
    }
}
