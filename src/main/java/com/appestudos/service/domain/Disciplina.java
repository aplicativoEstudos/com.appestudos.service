package com.appestudos.service.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_disciplina")
    private String nomeDisciplina;

    @Type(type = "uuid-char")
    @Column(name = "id_user", length = 36)
    private UUID idUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public Disciplina nomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        return this;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public Disciplina idUser(UUID idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplina)) {
            return false;
        }
        return id != null && id.equals(((Disciplina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", nomeDisciplina='" + getNomeDisciplina() + "'" +
            ", idUser='" + getIdUser() + "'" +
            "}";
    }
}
