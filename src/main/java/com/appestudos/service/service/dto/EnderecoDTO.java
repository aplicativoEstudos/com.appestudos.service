package com.appestudos.service.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.appestudos.service.domain.Endereco} entity.
 */
public class EnderecoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String cidade;

    @NotNull
    private String bairro;

    @NotNull
    private String rua;

    @NotNull
    private String cep;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoDTO)) {
            return false;
        }

        return id != null && id.equals(((EnderecoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoDTO{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", rua='" + getRua() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
