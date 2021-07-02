package com.appestudos.service.service.mapper;


import com.appestudos.service.domain.*;
import com.appestudos.service.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {



    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
