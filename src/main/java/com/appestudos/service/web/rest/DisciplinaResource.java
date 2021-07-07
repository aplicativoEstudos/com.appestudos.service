package com.appestudos.service.web.rest;

import com.appestudos.service.service.DisciplinaService;
import com.appestudos.service.web.rest.errors.BadRequestAlertException;
import com.appestudos.service.service.dto.DisciplinaDTO;
import com.appestudos.service.service.dto.DisciplinaCriteria;
import com.appestudos.service.service.DisciplinaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.appestudos.service.domain.Disciplina}.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "appestudosDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaService disciplinaService;

    private final DisciplinaQueryService disciplinaQueryService;

    public DisciplinaResource(DisciplinaService disciplinaService, DisciplinaQueryService disciplinaQueryService) {
        this.disciplinaService = disciplinaService;
        this.disciplinaQueryService = disciplinaQueryService;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinaDTO, or with status {@code 400 (Bad Request)} if the disciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaDTO> createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplinas} : Updates an existing disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplinas")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> getAllDisciplinas(DisciplinaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disciplinas by criteria: {}", criteria);
        Page<DisciplinaDTO> page = disciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplinas/count} : count all the disciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disciplinas/count")
    public ResponseEntity<Long> countDisciplinas(DisciplinaCriteria criteria) {
        log.debug("REST request to count Disciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(disciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<DisciplinaDTO> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Optional<DisciplinaDTO> disciplinaDTO = disciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplinaDTO);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
