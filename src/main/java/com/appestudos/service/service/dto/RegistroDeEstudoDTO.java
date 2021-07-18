package com.appestudos.service.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.appestudos.service.domain.RegistroDeEstudo} entity.
 */
public class RegistroDeEstudoDTO implements Serializable {
    
    private Long id;

    private Instant horaInicial;

    private String duracaoTempo;


    private Long areaId;
    
    private AreaDTO area;

    private Long disciplinaId;
    
    private DisciplinaDTO disciplina;

    private Long pessoaId;
    
    private PessoaDTO pessoa;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Instant horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getDuracaoTempo() {
        return duracaoTempo;
    }

    public void setDuracaoTempo(String duracaoTempo) {
        this.duracaoTempo = duracaoTempo;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }
    
    public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	public DisciplinaDTO getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(DisciplinaDTO disciplina) {
		this.disciplina = disciplina;
	}

	public PessoaDTO getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroDeEstudoDTO)) {
            return false;
        }

        return id != null && id.equals(((RegistroDeEstudoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDeEstudoDTO{" +
            "id=" + getId() +
            ", horaInicial='" + getHoraInicial() + "'" +
            ", duracaoTempo='" + getDuracaoTempo() + "'" +
            ", areaId=" + getAreaId() +
            ", disciplinaId=" + getDisciplinaId() +
            ", pessoaId=" + getPessoaId() +
            "}";
    }
}
