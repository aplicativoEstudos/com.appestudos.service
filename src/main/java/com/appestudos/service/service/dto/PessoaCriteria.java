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

/**
 * Criteria class for the {@link com.appestudos.service.domain.Pessoa} entity. This class is used
 * in {@link com.appestudos.service.web.rest.PessoaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PessoaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sobrenome;

    private StringFilter email;

    private LongFilter enderecoId;

    public PessoaCriteria() {
    }

    public PessoaCriteria(PessoaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.sobrenome = other.sobrenome == null ? null : other.sobrenome.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.enderecoId = other.enderecoId == null ? null : other.enderecoId.copy();
    }

    @Override
    public PessoaCriteria copy() {
        return new PessoaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(StringFilter sobrenome) {
        this.sobrenome = sobrenome;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(LongFilter enderecoId) {
        this.enderecoId = enderecoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PessoaCriteria that = (PessoaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sobrenome, that.sobrenome) &&
            Objects.equals(email, that.email) &&
            Objects.equals(enderecoId, that.enderecoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        sobrenome,
        email,
        enderecoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (sobrenome != null ? "sobrenome=" + sobrenome + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (enderecoId != null ? "enderecoId=" + enderecoId + ", " : "") +
            "}";
    }

}
