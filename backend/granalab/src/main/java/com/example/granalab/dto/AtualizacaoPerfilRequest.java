package com.example.granalab.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

/** DTO de PATCH que distingue propriedade omitida de propriedade nula. */
public class AtualizacaoPerfilRequest {

    private String nome;
    private String urlAvatar;
    private String dataNascimento;
    private String genero;
    private String escolaAtua;

    private boolean nomeInformado;
    private boolean urlAvatarInformado;
    private boolean dataNascimentoInformada;
    private boolean generoInformado;
    private boolean escolaAtuaInformada;

    @JsonSetter("nome")
    public void setNome(String nome) { this.nomeInformado = true; this.nome = nome; }
    @JsonSetter("urlAvatar")
    public void setUrlAvatar(String urlAvatar) { this.urlAvatarInformado = true; this.urlAvatar = urlAvatar; }
    @JsonSetter("dataNascimento")
    public void setDataNascimento(String dataNascimento) { this.dataNascimentoInformada = true; this.dataNascimento = dataNascimento; }
    @JsonSetter("genero")
    public void setGenero(String genero) { this.generoInformado = true; this.genero = genero; }
    @JsonSetter("escolaAtua")
    public void setEscolaAtua(String escolaAtua) { this.escolaAtuaInformada = true; this.escolaAtua = escolaAtua; }

    public String getNome() { return nome; }
    public String getUrlAvatar() { return urlAvatar; }
    public String getDataNascimento() { return dataNascimento; }
    public String getGenero() { return genero; }
    public String getEscolaAtua() { return escolaAtua; }
    public boolean isNomeInformado() { return nomeInformado; }
    public boolean isUrlAvatarInformado() { return urlAvatarInformado; }
    public boolean isDataNascimentoInformada() { return dataNascimentoInformada; }
    public boolean isGeneroInformado() { return generoInformado; }
    public boolean isEscolaAtuaInformada() { return escolaAtuaInformada; }
}
