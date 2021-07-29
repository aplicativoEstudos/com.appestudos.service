package com.appestudos.service.service.mapper;


import com.appestudos.service.domain.*;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaDisciplina} and its DTO {@link AreaDisciplinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaMapper.class, DisciplinaMapper.class})
public interface AreaDisciplinaMapper extends EntityMapper<AreaDisciplinaDTO, AreaDisciplina> {

    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "area.noma", target = "areaNoma")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nomeDisciplina", target = "disciplinaNomeDisciplina")
    AreaDisciplinaDTO toDto(AreaDisciplina areaDisciplina);

    @Mapping(source = "areaId", target = "area")
    @Mapping(source = "disciplinaId", target = "disciplina")
    AreaDisciplina toEntity(AreaDisciplinaDTO areaDisciplinaDTO);

    default AreaDisciplina fromId(Long id) {
        if (id == null) {
            return null;
        }
        AreaDisciplina areaDisciplina = new AreaDisciplina();
        areaDisciplina.setId(id);
        return areaDisciplina;
    }
}
