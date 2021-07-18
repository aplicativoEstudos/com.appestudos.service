package com.appestudos.service.service.mapper;


import com.appestudos.service.domain.*;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegistroDeEstudo} and its DTO {@link RegistroDeEstudoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaMapper.class, DisciplinaMapper.class, PessoaMapper.class})
public interface RegistroDeEstudoMapper extends EntityMapper<RegistroDeEstudoDTO, RegistroDeEstudo> {

    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina", target = "disciplina")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa", target = "pessoa")
    RegistroDeEstudoDTO toDto(RegistroDeEstudo registroDeEstudo);

    @Mapping(source = "areaId", target = "area.id")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "disciplinaId", target = "disciplina.id")
    @Mapping(source = "disciplina", target = "disciplina")
    @Mapping(source = "pessoaId", target = "pessoa.id")
    @Mapping(source = "pessoa", target = "pessoa")
    RegistroDeEstudo toEntity(RegistroDeEstudoDTO registroDeEstudoDTO);

    default RegistroDeEstudo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegistroDeEstudo registroDeEstudo = new RegistroDeEstudo();
        registroDeEstudo.setId(id);
        return registroDeEstudo;
    }
}
