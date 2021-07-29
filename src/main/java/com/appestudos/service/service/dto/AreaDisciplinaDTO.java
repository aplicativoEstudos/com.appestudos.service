package com.appestudos.service.service.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link com.appestudos.service.domain.AreaDisciplina} entity.
 */
public class AreaDisciplinaDTO implements Serializable {
    
    private Long id;

    private Boolean geral;

    private UUID idUser;


    private Long areaId;

    private String areaNoma;

    private Long disciplinaId;

    private String disciplinaNomeDisciplina;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isGeral() {
        return geral;
    }

    public void setGeral(Boolean geral) {
        this.geral = geral;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaNoma() {
        return areaNoma;
    }

    public void setAreaNoma(String areaNoma) {
        this.areaNoma = areaNoma;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getDisciplinaNomeDisciplina() {
        return disciplinaNomeDisciplina;
    }

    public void setDisciplinaNomeDisciplina(String disciplinaNomeDisciplina) {
        this.disciplinaNomeDisciplina = disciplinaNomeDisciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDisciplinaDTO)) {
            return false;
        }

        return id != null && id.equals(((AreaDisciplinaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDisciplinaDTO{" +
            "id=" + getId() +
            ", geral='" + isGeral() + "'" +
            ", idUser='" + getIdUser() + "'" +
            ", areaId=" + getAreaId() +
            ", areaNoma='" + getAreaNoma() + "'" +
            ", disciplinaId=" + getDisciplinaId() +
            ", disciplinaNomeDisciplina='" + getDisciplinaNomeDisciplina() + "'" +
            "}";
    }
}
