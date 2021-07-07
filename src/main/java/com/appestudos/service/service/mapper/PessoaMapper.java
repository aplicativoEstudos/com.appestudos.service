package com.appestudos.service.service.mapper;


import com.appestudos.service.domain.*;
import com.appestudos.service.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    PessoaDTO toDto(Pessoa pessoa);

    @Mapping(source = "enderecoId", target = "endereco")
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
