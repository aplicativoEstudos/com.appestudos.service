package com.appestudos.service.web.rest;

import com.appestudos.service.service.RegistroDeEstudoService;
import com.appestudos.service.web.rest.errors.BadRequestAlertException;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;
import com.appestudos.service.service.dto.RegistroDeEstudoCriteria;
import com.appestudos.service.service.RegistroDeEstudoQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.appestudos.service.domain.RegistroDeEstudo}.
 */
@RestController
@RequestMapping("/api")
public class RegistroDeEstudoResource {

    private final Logger log = LoggerFactory.getLogger(RegistroDeEstudoResource.class);

    private static final String ENTITY_NAME = "appestudosRegistroDeEstudo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroDeEstudoService registroDeEstudoService;

    private final RegistroDeEstudoQueryService registroDeEstudoQueryService;

    public RegistroDeEstudoResource(RegistroDeEstudoService registroDeEstudoService, RegistroDeEstudoQueryService registroDeEstudoQueryService) {
        this.registroDeEstudoService = registroDeEstudoService;
        this.registroDeEstudoQueryService = registroDeEstudoQueryService;
    }

    /**
     * {@code POST  /registro-de-estudos} : Create a new registroDeEstudo.
     *
     * @param registroDeEstudoDTO the registroDeEstudoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroDeEstudoDTO, or with status {@code 400 (Bad Request)} if the registroDeEstudo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registro-de-estudos")
    public ResponseEntity<RegistroDeEstudoDTO> createRegistroDeEstudo(@Valid @RequestBody RegistroDeEstudoDTO registroDeEstudoDTO) throws URISyntaxException {
        log.debug("REST request to save RegistroDeEstudo : {}", registroDeEstudoDTO);
        if (registroDeEstudoDTO.getId() != null) {
            throw new BadRequestAlertException("A new registroDeEstudo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        registroDeEstudoDTO.setHoraInicial(Instant.now());
        registroDeEstudoDTO.setHoraFinal(null);
        RegistroDeEstudoDTO result = registroDeEstudoService.save(registroDeEstudoDTO);
        return ResponseEntity.created(new URI("/api/registro-de-estudos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registro-de-estudos} : Updates an existing registroDeEstudo.
     *
     * @param registroDeEstudoDTO the registroDeEstudoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroDeEstudoDTO,
     * or with status {@code 400 (Bad Request)} if the registroDeEstudoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroDeEstudoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registro-de-estudos")
    public ResponseEntity<RegistroDeEstudoDTO> updateRegistroDeEstudo(@Valid @RequestBody RegistroDeEstudoDTO registroDeEstudoDTO) throws URISyntaxException {
        log.debug("REST request to update RegistroDeEstudo : {}", registroDeEstudoDTO);
        if (registroDeEstudoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        registroDeEstudoDTO.setHoraFinal(Instant.now());
        Duration duration = Duration.between(registroDeEstudoDTO.getHoraInicial(),registroDeEstudoDTO.getHoraFinal());
        registroDeEstudoDTO.setDuracaoTempo(String.format(duration.toHours()+":"+duration.toMinutesPart()+":"+
                duration.toSecondsPart()));
        RegistroDeEstudoDTO result = registroDeEstudoService.save(registroDeEstudoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroDeEstudoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registro-de-estudos} : get all the registroDeEstudos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registroDeEstudos in body.
     */
    @GetMapping("/registro-de-estudos")
    public ResponseEntity<List<RegistroDeEstudoDTO>> getAllRegistroDeEstudos(RegistroDeEstudoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegistroDeEstudos by criteria: {}", criteria);
        Page<RegistroDeEstudoDTO> page = registroDeEstudoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registro-de-estudos/count} : count all the registroDeEstudos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/registro-de-estudos/count")
    public ResponseEntity<Long> countRegistroDeEstudos(RegistroDeEstudoCriteria criteria) {
        log.debug("REST request to count RegistroDeEstudos by criteria: {}", criteria);
        return ResponseEntity.ok().body(registroDeEstudoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /registro-de-estudos/:id} : get the "id" registroDeEstudo.
     *
     * @param id the id of the registroDeEstudoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroDeEstudoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registro-de-estudos/{id}")
    public ResponseEntity<RegistroDeEstudoDTO> getRegistroDeEstudo(@PathVariable Long id) {
        log.debug("REST request to get RegistroDeEstudo : {}", id);
        Optional<RegistroDeEstudoDTO> registroDeEstudoDTO = registroDeEstudoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroDeEstudoDTO);
    }

    /**
     * {@code DELETE  /registro-de-estudos/:id} : delete the "id" registroDeEstudo.
     *
     * @param id the id of the registroDeEstudoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registro-de-estudos/{id}")
    public ResponseEntity<Void> deleteRegistroDeEstudo(@PathVariable Long id) {
        log.debug("REST request to delete RegistroDeEstudo : {}", id);
        registroDeEstudoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
