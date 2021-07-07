package com.appestudos.service.service.mapper;


import com.appestudos.service.domain.*;
import com.appestudos.service.service.dto.DisciplinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disciplina} and its DTO {@link DisciplinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {



    default Disciplina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setId(id);
        return disciplina;
    }
}
