package com.appestudos.service.service.impl;

import com.appestudos.service.service.AreaDisciplinaService;
import com.appestudos.service.domain.AreaDisciplina;
import com.appestudos.service.repository.AreaDisciplinaRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;
import com.appestudos.service.service.mapper.AreaDisciplinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link AreaDisciplina}.
 */
@Service
@Transactional
public class AreaDisciplinaServiceImpl implements AreaDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(AreaDisciplinaServiceImpl.class);

    private final AreaDisciplinaRepository areaDisciplinaRepository;

    private final AreaDisciplinaMapper areaDisciplinaMapper;
    
    private static final String SUB = "id_user";

    public AreaDisciplinaServiceImpl(AreaDisciplinaRepository areaDisciplinaRepository, AreaDisciplinaMapper areaDisciplinaMapper) {
        this.areaDisciplinaRepository = areaDisciplinaRepository;
        this.areaDisciplinaMapper = areaDisciplinaMapper;
    }

    @Override
    public AreaDisciplinaDTO save(AreaDisciplinaDTO areaDisciplinaDTO) {
        log.debug("Request to save AreaDisciplina : {}", areaDisciplinaDTO);
        AreaDisciplina areaDisciplina = areaDisciplinaMapper.toEntity(areaDisciplinaDTO);
        areaDisciplina.setIdUser(UUID.fromString(((Optional<Map<String, String>>)SecurityUtils.getCurrentLoginMatricula()).get().get(SUB)));;
        areaDisciplina = areaDisciplinaRepository.save(areaDisciplina);
        return areaDisciplinaMapper.toDto(areaDisciplina);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AreaDisciplinas");
        return areaDisciplinaRepository.findAll(pageable)
            .map(areaDisciplinaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AreaDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get AreaDisciplina : {}", id);
        return areaDisciplinaRepository.findById(id)
            .map(areaDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AreaDisciplina : {}", id);
        areaDisciplinaRepository.deleteById(id);
    }
}
