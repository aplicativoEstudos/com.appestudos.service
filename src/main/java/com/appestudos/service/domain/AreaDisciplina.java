package com.appestudos.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A AreaDisciplina.
 */
@Entity
@Table(name = "area_disciplina")
public class AreaDisciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geral")
    private Boolean geral;

    @Type(type = "uuid-char")
    @Column(name = "id_user", length = 36)
    private UUID idUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "areaDisciplinas", allowSetters = true)
    private Area area;

    @ManyToOne
    @JsonIgnoreProperties(value = "areaDisciplinas", allowSetters = true)
    private Disciplina disciplina;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isGeral() {
        return geral;
    }

    public AreaDisciplina geral(Boolean geral) {
        this.geral = geral;
        return this;
    }

    public void setGeral(Boolean geral) {
        this.geral = geral;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public AreaDisciplina idUser(UUID idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public Area getArea() {
        return area;
    }

    public AreaDisciplina area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public AreaDisciplina disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDisciplina)) {
            return false;
        }
        return id != null && id.equals(((AreaDisciplina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDisciplina{" +
            "id=" + getId() +
            ", geral='" + isGeral() + "'" +
            ", idUser='" + getIdUser() + "'" +
            "}";
    }
}
