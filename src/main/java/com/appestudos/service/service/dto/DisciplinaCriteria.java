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
 * Criteria class for the {@link com.appestudos.service.domain.Disciplina} entity. This class is used
 * in {@link com.appestudos.service.web.rest.DisciplinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disciplinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisciplinaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeDisciplina;

    private UUIDFilter idUser;

    public DisciplinaCriteria() {
    }

    public DisciplinaCriteria(DisciplinaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeDisciplina = other.nomeDisciplina == null ? null : other.nomeDisciplina.copy();
        this.idUser = other.idUser == null ? null : other.idUser.copy();
    }

    @Override
    public DisciplinaCriteria copy() {
        return new DisciplinaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(StringFilter nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public UUIDFilter getIdUser() {
        return idUser;
    }

    public void setIdUser(UUIDFilter idUser) {
        this.idUser = idUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisciplinaCriteria that = (DisciplinaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomeDisciplina, that.nomeDisciplina) &&
            Objects.equals(idUser, that.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomeDisciplina,
        idUser
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomeDisciplina != null ? "nomeDisciplina=" + nomeDisciplina + ", " : "") +
                (idUser != null ? "idUser=" + idUser + ", " : "") +
            "}";
    }

}
