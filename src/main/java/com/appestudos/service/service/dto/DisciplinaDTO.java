package com.appestudos.service.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.appestudos.service.domain.Disciplina} entity.
 */
public class DisciplinaDTO implements Serializable {
    
    private Long id;

    private String nomeDisciplina;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisciplinaDTO)) {
            return false;
        }

        return id != null && id.equals(((DisciplinaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaDTO{" +
            "id=" + getId() +
            ", nomeDisciplina='" + getNomeDisciplina() + "'" +
            "}";
    }
}
