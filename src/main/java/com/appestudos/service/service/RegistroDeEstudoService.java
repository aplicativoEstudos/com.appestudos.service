package com.appestudos.service.service;

import com.appestudos.service.service.dto.RegistroDeEstudoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.appestudos.service.domain.RegistroDeEstudo}.
 */
public interface RegistroDeEstudoService {

    /**
     * Save a registroDeEstudo.
     *
     * @param registroDeEstudoDTO the entity to save.
     * @return the persisted entity.
     */
    RegistroDeEstudoDTO save(RegistroDeEstudoDTO registroDeEstudoDTO);

    /**
     * Get all the registroDeEstudos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegistroDeEstudoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" registroDeEstudo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistroDeEstudoDTO> findOne(Long id);

    /**
     * Delete the "id" registroDeEstudo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    RegistroDeEstudoDTO start(RegistroDeEstudoDTO registroDeEstudoDTO);
    
    
    RegistroDeEstudoDTO stop(RegistroDeEstudoDTO registroDeEstudoDTO);
}
