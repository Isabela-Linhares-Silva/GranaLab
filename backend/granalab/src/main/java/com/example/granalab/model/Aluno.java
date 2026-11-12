package com.example.granalab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/** Perfil de aluno (PK = FK para usuario.id_user). */
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @Column(name = "id_aluno", nullable = false)
    private Integer idAluno;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id_aluno")
    private Usuario usuario;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nivel_perfil", nullable = false, length = 100)
    private String nivelPerfil;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    public Aluno() {
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNivelPerfil() {
        return nivelPerfil;
    }

    public void setNivelPerfil(String nivelPerfil) {
        this.nivelPerfil = nivelPerfil;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
