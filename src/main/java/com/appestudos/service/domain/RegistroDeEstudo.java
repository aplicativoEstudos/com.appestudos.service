package com.appestudos.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A RegistroDeEstudo.
 */
@Entity
@Table(name = "registro_de_estudo")
public class RegistroDeEstudo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_inicial")
    private Instant horaInicial;

    @Column(name = "hora_final")
    private Instant horaFinal;

    @Column(name = "duracao_tempo")
    private String duracaoTempo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tempoDeEstudos", allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tempoDeEstudos", allowSetters = true)
    private Disciplina disciplina;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "tempoDeEstudos", allowSetters = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicial() {
        return horaInicial;
    }

    public RegistroDeEstudo horaInicial(Instant horaInicial) {
        this.horaInicial = horaInicial;
        return this;
    }

    public void setHoraInicial(Instant horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Instant getHoraFinal() {
        return horaFinal;
    }

    public RegistroDeEstudo horaFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
        return this;
    }

    public void setHoraFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getDuracaoTempo() {
        return duracaoTempo;
    }

    public RegistroDeEstudo duracaoTempo(String duracaoTempo) {
        this.duracaoTempo = duracaoTempo;
        return this;
    }

    public void setDuracaoTempo(String duracaoTempo) {
        this.duracaoTempo = duracaoTempo;
    }

    public Area getArea() {
        return area;
    }

    public RegistroDeEstudo area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public RegistroDeEstudo disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public RegistroDeEstudo pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroDeEstudo)) {
            return false;
        }
        return id != null && id.equals(((RegistroDeEstudo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDeEstudo{" +
            "id=" + getId() +
            ", horaInicial='" + getHoraInicial() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", duracaoTempo='" + getDuracaoTempo() + "'" +
            "}";
    }
}
