package com.appestudos.service.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.appestudos.service.domain.Pessoa} entity.
 */
public class PessoaDTO implements Serializable {
    
    private Long id;

    @Lob
    private byte[] foto;

    private String fotoContentType;
    @NotNull
    private String nome;

    @NotNull
    private String sobrenome;

    @NotNull
    @Pattern(regexp = "^\\S+@\\S+$")
    private String email;

    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}\\-\\d{4}")
    private String telefone;

    
    private UUID idUser;


    private Long enderecoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaDTO)) {
            return false;
        }

        return id != null && id.equals(((PessoaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaDTO{" +
            "id=" + getId() +
            ", foto='" + getFoto() + "'" +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", idUser='" + getIdUser() + "'" +
            ", enderecoId=" + getEnderecoId() +
            "}";
    }
}
