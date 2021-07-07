package com.appestudos.service.service;

import com.appestudos.service.service.dto.DisciplinaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.appestudos.service.domain.Disciplina}.
 */
public interface DisciplinaService {

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    DisciplinaDTO save(DisciplinaDTO disciplinaDTO);

    /**
     * Get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplinaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" disciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" disciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
