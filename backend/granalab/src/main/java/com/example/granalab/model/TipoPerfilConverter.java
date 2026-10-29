package com.example.granalab.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** Converte TipoPerfil <-> 'Mentor'/'Aluno' (valores exatos do ENUM do MySQL). */
@Converter
public class TipoPerfilConverter implements AttributeConverter<TipoPerfil, String> {

    @Override
    public String convertToDatabaseColumn(TipoPerfil attribute) {
        return attribute == null ? null : attribute.getValor();
    }

    @Override
    public TipoPerfil convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoPerfil.fromValor(dbData);
    }
}
