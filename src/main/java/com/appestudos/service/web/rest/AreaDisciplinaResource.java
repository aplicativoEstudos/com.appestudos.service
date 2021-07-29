package com.appestudos.service.web.rest;

import com.appestudos.service.service.AreaDisciplinaService;
import com.appestudos.service.web.rest.errors.BadRequestAlertException;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;
import com.appestudos.service.service.dto.AreaDisciplinaCriteria;
import com.appestudos.service.service.AreaDisciplinaQueryService;

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
 * REST controller for managing {@link com.appestudos.service.domain.AreaDisciplina}.
 */
@RestController
@RequestMapping("/api")
public class AreaDisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(AreaDisciplinaResource.class);

    private static final String ENTITY_NAME = "appestudosAreaDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaDisciplinaService areaDisciplinaService;

    private final AreaDisciplinaQueryService areaDisciplinaQueryService;

    public AreaDisciplinaResource(AreaDisciplinaService areaDisciplinaService, AreaDisciplinaQueryService areaDisciplinaQueryService) {
        this.areaDisciplinaService = areaDisciplinaService;
        this.areaDisciplinaQueryService = areaDisciplinaQueryService;
    }

    /**
     * {@code POST  /area-disciplinas} : Create a new areaDisciplina.
     *
     * @param areaDisciplinaDTO the areaDisciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaDisciplinaDTO, or with status {@code 400 (Bad Request)} if the areaDisciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-disciplinas")
    public ResponseEntity<AreaDisciplinaDTO> createAreaDisciplina(@RequestBody AreaDisciplinaDTO areaDisciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save AreaDisciplina : {}", areaDisciplinaDTO);
        if (areaDisciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaDisciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaDisciplinaDTO result = areaDisciplinaService.save(areaDisciplinaDTO);
        return ResponseEntity.created(new URI("/api/area-disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-disciplinas} : Updates an existing areaDisciplina.
     *
     * @param areaDisciplinaDTO the areaDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the areaDisciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-disciplinas")
    public ResponseEntity<AreaDisciplinaDTO> updateAreaDisciplina(@RequestBody AreaDisciplinaDTO areaDisciplinaDTO) throws URISyntaxException {
        log.debug("REST request to update AreaDisciplina : {}", areaDisciplinaDTO);
        if (areaDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AreaDisciplinaDTO result = areaDisciplinaService.save(areaDisciplinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDisciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /area-disciplinas} : get all the areaDisciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaDisciplinas in body.
     */
    @GetMapping("/area-disciplinas")
    public ResponseEntity<List<AreaDisciplinaDTO>> getAllAreaDisciplinas(AreaDisciplinaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AreaDisciplinas by criteria: {}", criteria);
        Page<AreaDisciplinaDTO> page = areaDisciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /area-disciplinas/count} : count all the areaDisciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/area-disciplinas/count")
    public ResponseEntity<Long> countAreaDisciplinas(AreaDisciplinaCriteria criteria) {
        log.debug("REST request to count AreaDisciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(areaDisciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /area-disciplinas/:id} : get the "id" areaDisciplina.
     *
     * @param id the id of the areaDisciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaDisciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-disciplinas/{id}")
    public ResponseEntity<AreaDisciplinaDTO> getAreaDisciplina(@PathVariable Long id) {
        log.debug("REST request to get AreaDisciplina : {}", id);
        Optional<AreaDisciplinaDTO> areaDisciplinaDTO = areaDisciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaDisciplinaDTO);
    }

    /**
     * {@code DELETE  /area-disciplinas/:id} : delete the "id" areaDisciplina.
     *
     * @param id the id of the areaDisciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-disciplinas/{id}")
    public ResponseEntity<Void> deleteAreaDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete AreaDisciplina : {}", id);
        areaDisciplinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
