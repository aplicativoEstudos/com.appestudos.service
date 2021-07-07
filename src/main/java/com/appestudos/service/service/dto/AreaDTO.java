package com.appestudos.service.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.appestudos.service.domain.Area} entity.
 */
public class AreaDTO implements Serializable {
    
    private Long id;

    private String noma;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoma() {
        return noma;
    }

    public void setNoma(String noma) {
        this.noma = noma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDTO)) {
            return false;
        }

        return id != null && id.equals(((AreaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDTO{" +
            "id=" + getId() +
            ", noma='" + getNoma() + "'" +
            "}";
    }
}
