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
import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.appestudos.service.domain.AreaDisciplina} entity. This class is used
 * in {@link com.appestudos.service.web.rest.AreaDisciplinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /area-disciplinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AreaDisciplinaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter geral;

    private UUIDFilter idUser;

    private LongFilter areaId;

    private LongFilter disciplinaId;

    public AreaDisciplinaCriteria() {
    }

    public AreaDisciplinaCriteria(AreaDisciplinaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.geral = other.geral == null ? null : other.geral.copy();
        this.idUser = other.idUser == null ? null : other.idUser.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.disciplinaId = other.disciplinaId == null ? null : other.disciplinaId.copy();
    }

    @Override
    public AreaDisciplinaCriteria copy() {
        return new AreaDisciplinaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getGeral() {
        return geral;
    }

    public void setGeral(BooleanFilter geral) {
        this.geral = geral;
    }

    public UUIDFilter getIdUser() {
        return idUser;
    }

    public void setIdUser(UUIDFilter idUser) {
        this.idUser = idUser;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AreaDisciplinaCriteria that = (AreaDisciplinaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(geral, that.geral) &&
            Objects.equals(idUser, that.idUser) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(disciplinaId, that.disciplinaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        geral,
        idUser,
        areaId,
        disciplinaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDisciplinaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (geral != null ? "geral=" + geral + ", " : "") +
                (idUser != null ? "idUser=" + idUser + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (disciplinaId != null ? "disciplinaId=" + disciplinaId + ", " : "") +
            "}";
    }

}
