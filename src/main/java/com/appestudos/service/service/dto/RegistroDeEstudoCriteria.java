package com.appestudos.service.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.appestudos.service.domain.RegistroDeEstudo} entity. This class is used
 * in {@link com.appestudos.service.web.rest.RegistroDeEstudoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /registro-de-estudos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegistroDeEstudoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter data;

    private InstantFilter horaInicial;

    private InstantFilter horaFinal;

    private StringFilter duracaoTempo;

    private LongFilter areaId;

    private LongFilter disciplinaId;

    private LongFilter pessoaId;

    public RegistroDeEstudoCriteria() {
    }

    public RegistroDeEstudoCriteria(RegistroDeEstudoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.horaInicial = other.horaInicial == null ? null : other.horaInicial.copy();
        this.horaFinal = other.horaFinal == null ? null : other.horaFinal.copy();
        this.duracaoTempo = other.duracaoTempo == null ? null : other.duracaoTempo.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.disciplinaId = other.disciplinaId == null ? null : other.disciplinaId.copy();
        this.pessoaId = other.pessoaId == null ? null : other.pessoaId.copy();
    }

    @Override
    public RegistroDeEstudoCriteria copy() {
        return new RegistroDeEstudoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getData() {
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public InstantFilter getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(InstantFilter horaInicial) {
        this.horaInicial = horaInicial;
    }

    public InstantFilter getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(InstantFilter horaFinal) {
        this.horaFinal = horaFinal;
    }

    public StringFilter getDuracaoTempo() {
        return duracaoTempo;
    }

    public void setDuracaoTempo(StringFilter duracaoTempo) {
        this.duracaoTempo = duracaoTempo;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(LongFilter disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegistroDeEstudoCriteria that = (RegistroDeEstudoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(data, that.data) &&
            Objects.equals(horaInicial, that.horaInicial) &&
            Objects.equals(horaFinal, that.horaFinal) &&
            Objects.equals(duracaoTempo, that.duracaoTempo) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(disciplinaId, that.disciplinaId) &&
            Objects.equals(pessoaId, that.pessoaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        data,
        horaInicial,
        horaFinal,
        duracaoTempo,
        areaId,
        disciplinaId,
        pessoaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDeEstudoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (horaInicial != null ? "horaInicial=" + horaInicial + ", " : "") +
                (horaFinal != null ? "horaFinal=" + horaFinal + ", " : "") +
                (duracaoTempo != null ? "duracaoTempo=" + duracaoTempo + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (disciplinaId != null ? "disciplinaId=" + disciplinaId + ", " : "") +
                (pessoaId != null ? "pessoaId=" + pessoaId + ", " : "") +
            "}";
    }

}
