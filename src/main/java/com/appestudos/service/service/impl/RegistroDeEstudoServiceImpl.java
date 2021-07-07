package com.appestudos.service.service.impl;

import com.appestudos.service.service.RegistroDeEstudoService;
import com.appestudos.service.domain.RegistroDeEstudo;
import com.appestudos.service.repository.RegistroDeEstudoRepository;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;
import com.appestudos.service.service.mapper.RegistroDeEstudoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RegistroDeEstudo}.
 */
@Service
@Transactional
public class RegistroDeEstudoServiceImpl implements RegistroDeEstudoService {

    private final Logger log = LoggerFactory.getLogger(RegistroDeEstudoServiceImpl.class);

    private final RegistroDeEstudoRepository registroDeEstudoRepository;

    private final RegistroDeEstudoMapper registroDeEstudoMapper;

    public RegistroDeEstudoServiceImpl(RegistroDeEstudoRepository registroDeEstudoRepository, RegistroDeEstudoMapper registroDeEstudoMapper) {
        this.registroDeEstudoRepository = registroDeEstudoRepository;
        this.registroDeEstudoMapper = registroDeEstudoMapper;
    }

    @Override
    public RegistroDeEstudoDTO save(RegistroDeEstudoDTO registroDeEstudoDTO) {
        log.debug("Request to save RegistroDeEstudo : {}", registroDeEstudoDTO);
        RegistroDeEstudo registroDeEstudo = registroDeEstudoMapper.toEntity(registroDeEstudoDTO);
        registroDeEstudo = registroDeEstudoRepository.save(registroDeEstudo);
        return registroDeEstudoMapper.toDto(registroDeEstudo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegistroDeEstudoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroDeEstudos");
        return registroDeEstudoRepository.findAll(pageable)
            .map(registroDeEstudoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroDeEstudoDTO> findOne(Long id) {
        log.debug("Request to get RegistroDeEstudo : {}", id);
        return registroDeEstudoRepository.findById(id)
            .map(registroDeEstudoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistroDeEstudo : {}", id);
        registroDeEstudoRepository.deleteById(id);
    }
}
