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
 * Criteria class for the {@link com.appestudos.service.domain.Endereco} entity. This class is used
 * in {@link com.appestudos.service.web.rest.EnderecoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enderecos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnderecoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cidade;

    private StringFilter bairro;

    private StringFilter rua;

    private StringFilter cep;

    private IntegerFilter numero;

    public EnderecoCriteria() {
    }

    public EnderecoCriteria(EnderecoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cidade = other.cidade == null ? null : other.cidade.copy();
        this.bairro = other.bairro == null ? null : other.bairro.copy();
        this.rua = other.rua == null ? null : other.rua.copy();
        this.cep = other.cep == null ? null : other.cep.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
    }

    @Override
    public EnderecoCriteria copy() {
        return new EnderecoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCidade() {
        return cidade;
    }

    public void setCidade(StringFilter cidade) {
        this.cidade = cidade;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getRua() {
        return rua;
    }

    public void setRua(StringFilter rua) {
        this.rua = rua;
    }

    public StringFilter getCep() {
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnderecoCriteria that = (EnderecoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cidade, that.cidade) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(rua, that.rua) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cidade,
        bairro,
        rua,
        cep,
        numero
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cidade != null ? "cidade=" + cidade + ", " : "") +
                (bairro != null ? "bairro=" + bairro + ", " : "") +
                (rua != null ? "rua=" + rua + ", " : "") +
                (cep != null ? "cep=" + cep + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
            "}";
    }

}
