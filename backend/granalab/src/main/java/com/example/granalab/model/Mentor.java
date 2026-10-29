package com.example.granalab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/** Perfil de mentor/professor (PK = FK para usuario.id_user). */
@Entity
@Table(name = "mentor")
public class Mentor {

    @Id
    @Column(name = "id_mentor", nullable = false)
    private Integer idMentor;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id_mentor")
    private Usuario usuario;

    @NotBlank
    @Size(max = 100)
    @Column(name = "escola_atua", nullable = false, length = 100)
    private String escolaAtua;

    public Mentor() {
    }

    public Integer getIdMentor() {
        return idMentor;
    }

    public void setIdMentor(Integer idMentor) {
        this.idMentor = idMentor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEscolaAtua() {
        return escolaAtua;
    }

    public void setEscolaAtua(String escolaAtua) {
        this.escolaAtua = escolaAtua;
    }
}
