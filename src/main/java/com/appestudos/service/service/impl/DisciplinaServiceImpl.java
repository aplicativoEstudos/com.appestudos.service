package com.appestudos.service.service.impl;

import com.appestudos.service.service.DisciplinaService;
import com.appestudos.service.domain.Disciplina;
import com.appestudos.service.repository.DisciplinaRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.DisciplinaDTO;
import com.appestudos.service.service.mapper.DisciplinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Disciplina}.
 */
@Service
@Transactional
public class DisciplinaServiceImpl implements DisciplinaService {

    private final Logger log = LoggerFactory.getLogger(DisciplinaServiceImpl.class);
    
    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaMapper disciplinaMapper;
    
    private static final String SUB = "id_user";

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
    }

    @Override
    public DisciplinaDTO save(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to save Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplina.setIdUser(UUID.fromString(((Optional<Map<String, String>>)SecurityUtils.getCurrentLoginMatricula()).get().get(SUB)));
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDto(disciplina);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplinas");
        return disciplinaRepository.findAll(pageable)
            .map(disciplinaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplinaDTO> findOne(Long id) {
        log.debug("Request to get Disciplina : {}", id);
        return disciplinaRepository.findById(id)
            .map(disciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disciplina : {}", id);
        disciplinaRepository.deleteById(id);
    }
}
