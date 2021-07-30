package com.appestudos.service.service;

import com.appestudos.service.service.dto.AreaDisciplinaCriteria;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.appestudos.service.domain.AreaDisciplina}.
 */
public interface AreaDisciplinaService {

    /**
     * Save a areaDisciplina.
     *
     * @param areaDisciplinaDTO the entity to save.
     * @return the persisted entity.
     */
    AreaDisciplinaDTO save(AreaDisciplinaDTO areaDisciplinaDTO);

    /**
     * Get all the areaDisciplinas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AreaDisciplinaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" areaDisciplina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AreaDisciplinaDTO> findOne(Long id);

    /**
     * Delete the "id" areaDisciplina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<AreaDisciplinaDTO> findAllComGeral(AreaDisciplinaCriteria criteria);
}
