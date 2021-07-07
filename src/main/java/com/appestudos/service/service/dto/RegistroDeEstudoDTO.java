package com.appestudos.service.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.appestudos.service.domain.RegistroDeEstudo} entity.
 */
public class RegistroDeEstudoDTO implements Serializable {
    
    private Long id;

    private LocalDate data;

    private Instant horaInicial;

    private Instant horaFinal;

    private String duracaoTempo;


    private Long areaId;

    private Long disciplinaId;

    private Long pessoaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Instant getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Instant horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Instant getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getDuracaoTempo() {
        return duracaoTempo;
    }

    public void setDuracaoTempo(String duracaoTempo) {
        this.duracaoTempo = duracaoTempo;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroDeEstudoDTO)) {
            return false;
        }

        return id != null && id.equals(((RegistroDeEstudoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDeEstudoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", horaInicial='" + getHoraInicial() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", duracaoTempo='" + getDuracaoTempo() + "'" +
            ", areaId=" + getAreaId() +
            ", disciplinaId=" + getDisciplinaId() +
            ", pessoaId=" + getPessoaId() +
            "}";
    }
}
