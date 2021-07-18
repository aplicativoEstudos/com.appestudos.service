package com.appestudos.service.web.rest;

import com.appestudos.service.AppestudosApp;
import com.appestudos.service.config.TestSecurityConfiguration;
import com.appestudos.service.domain.RegistroDeEstudo;
import com.appestudos.service.domain.Area;
import com.appestudos.service.domain.Disciplina;
import com.appestudos.service.domain.Pessoa;
import com.appestudos.service.repository.RegistroDeEstudoRepository;
import com.appestudos.service.service.RegistroDeEstudoService;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;
import com.appestudos.service.service.mapper.RegistroDeEstudoMapper;
import com.appestudos.service.service.dto.RegistroDeEstudoCriteria;
import com.appestudos.service.service.RegistroDeEstudoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegistroDeEstudoResource} REST controller.
 */
@SpringBootTest(classes = { AppestudosApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class RegistroDeEstudoResourceIT {

    private static final Instant DEFAULT_HORA_INICIAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DURACAO_TEMPO = "AAAAAAAAAA";
    private static final String UPDATED_DURACAO_TEMPO = "BBBBBBBBBB";

    @Autowired
    private RegistroDeEstudoRepository registroDeEstudoRepository;

    @Autowired
    private RegistroDeEstudoMapper registroDeEstudoMapper;

    @Autowired
    private RegistroDeEstudoService registroDeEstudoService;

    @Autowired
    private RegistroDeEstudoQueryService registroDeEstudoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistroDeEstudoMockMvc;

    private RegistroDeEstudo registroDeEstudo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroDeEstudo createEntity(EntityManager em) {
        RegistroDeEstudo registroDeEstudo = new RegistroDeEstudo()
            .horaInicial(DEFAULT_HORA_INICIAL)
            .duracaoTempo(DEFAULT_DURACAO_TEMPO);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        registroDeEstudo.setArea(area);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        registroDeEstudo.setDisciplina(disciplina);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        registroDeEstudo.setPessoa(pessoa);
        return registroDeEstudo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroDeEstudo createUpdatedEntity(EntityManager em) {
        RegistroDeEstudo registroDeEstudo = new RegistroDeEstudo()
            .horaInicial(UPDATED_HORA_INICIAL)
            .duracaoTempo(UPDATED_DURACAO_TEMPO);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        registroDeEstudo.setArea(area);
        // Add required entity
        Disciplina disciplina;
        if (TestUtil.findAll(em, Disciplina.class).isEmpty()) {
            disciplina = DisciplinaResourceIT.createUpdatedEntity(em);
            em.persist(disciplina);
            em.flush();
        } else {
            disciplina = TestUtil.findAll(em, Disciplina.class).get(0);
        }
        registroDeEstudo.setDisciplina(disciplina);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        registroDeEstudo.setPessoa(pessoa);
        return registroDeEstudo;
    }

    @BeforeEach
    public void initTest() {
        registroDeEstudo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistroDeEstudo() throws Exception {
        int databaseSizeBeforeCreate = registroDeEstudoRepository.findAll().size();
        // Create the RegistroDeEstudo
        RegistroDeEstudoDTO registroDeEstudoDTO = registroDeEstudoMapper.toDto(registroDeEstudo);
        restRegistroDeEstudoMockMvc.perform(post("/api/registro-de-estudos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registroDeEstudoDTO)))
            .andExpect(status().isCreated());

        // Validate the RegistroDeEstudo in the database
        List<RegistroDeEstudo> registroDeEstudoList = registroDeEstudoRepository.findAll();
        assertThat(registroDeEstudoList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroDeEstudo testRegistroDeEstudo = registroDeEstudoList.get(registroDeEstudoList.size() - 1);
        assertThat(testRegistroDeEstudo.getHoraInicial()).isEqualTo(DEFAULT_HORA_INICIAL);
        assertThat(testRegistroDeEstudo.getDuracaoTempo()).isEqualTo(DEFAULT_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void createRegistroDeEstudoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registroDeEstudoRepository.findAll().size();

        // Create the RegistroDeEstudo with an existing ID
        registroDeEstudo.setId(1L);
        RegistroDeEstudoDTO registroDeEstudoDTO = registroDeEstudoMapper.toDto(registroDeEstudo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroDeEstudoMockMvc.perform(post("/api/registro-de-estudos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registroDeEstudoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeEstudo in the database
        List<RegistroDeEstudo> registroDeEstudoList = registroDeEstudoRepository.findAll();
        assertThat(registroDeEstudoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegistroDeEstudos() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroDeEstudo.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicial").value(hasItem(DEFAULT_HORA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].duracaoTempo").value(hasItem(DEFAULT_DURACAO_TEMPO)));
    }
    
    @Test
    @Transactional
    public void getRegistroDeEstudo() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get the registroDeEstudo
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos/{id}", registroDeEstudo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registroDeEstudo.getId().intValue()))
            .andExpect(jsonPath("$.horaInicial").value(DEFAULT_HORA_INICIAL.toString()))
            .andExpect(jsonPath("$.duracaoTempo").value(DEFAULT_DURACAO_TEMPO));
    }


    @Test
    @Transactional
    public void getRegistroDeEstudosByIdFiltering() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        Long id = registroDeEstudo.getId();

        defaultRegistroDeEstudoShouldBeFound("id.equals=" + id);
        defaultRegistroDeEstudoShouldNotBeFound("id.notEquals=" + id);

        defaultRegistroDeEstudoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegistroDeEstudoShouldNotBeFound("id.greaterThan=" + id);

        defaultRegistroDeEstudoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegistroDeEstudoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaInicial equals to DEFAULT_HORA_INICIAL
        defaultRegistroDeEstudoShouldBeFound("horaInicial.equals=" + DEFAULT_HORA_INICIAL);

        // Get all the registroDeEstudoList where horaInicial equals to UPDATED_HORA_INICIAL
        defaultRegistroDeEstudoShouldNotBeFound("horaInicial.equals=" + UPDATED_HORA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraInicialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaInicial not equals to DEFAULT_HORA_INICIAL
        defaultRegistroDeEstudoShouldNotBeFound("horaInicial.notEquals=" + DEFAULT_HORA_INICIAL);

        // Get all the registroDeEstudoList where horaInicial not equals to UPDATED_HORA_INICIAL
        defaultRegistroDeEstudoShouldBeFound("horaInicial.notEquals=" + UPDATED_HORA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraInicialIsInShouldWork() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaInicial in DEFAULT_HORA_INICIAL or UPDATED_HORA_INICIAL
        defaultRegistroDeEstudoShouldBeFound("horaInicial.in=" + DEFAULT_HORA_INICIAL + "," + UPDATED_HORA_INICIAL);

        // Get all the registroDeEstudoList where horaInicial equals to UPDATED_HORA_INICIAL
        defaultRegistroDeEstudoShouldNotBeFound("horaInicial.in=" + UPDATED_HORA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaInicial is not null
        defaultRegistroDeEstudoShouldBeFound("horaInicial.specified=true");

        // Get all the registroDeEstudoList where horaInicial is null
        defaultRegistroDeEstudoShouldNotBeFound("horaInicial.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaFinal equals to DEFAULT_HORA_FINAL


        // Get all the registroDeEstudoList where horaFinal equals to UPDATED_HORA_FINAL
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaFinal not equals to DEFAULT_HORA_FINAL

        // Get all the registroDeEstudoList where horaFinal not equals to UPDATED_HORA_FINAL
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraFinalIsInShouldWork() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaFinal in DEFAULT_HORA_FINAL or UPDATED_HORA_FINAL

        // Get all the registroDeEstudoList where horaFinal equals to UPDATED_HORA_FINAL
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByHoraFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where horaFinal is not null
        defaultRegistroDeEstudoShouldBeFound("horaFinal.specified=true");

        // Get all the registroDeEstudoList where horaFinal is null
        defaultRegistroDeEstudoShouldNotBeFound("horaFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoIsEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo equals to DEFAULT_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.equals=" + DEFAULT_DURACAO_TEMPO);

        // Get all the registroDeEstudoList where duracaoTempo equals to UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.equals=" + UPDATED_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo not equals to DEFAULT_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.notEquals=" + DEFAULT_DURACAO_TEMPO);

        // Get all the registroDeEstudoList where duracaoTempo not equals to UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.notEquals=" + UPDATED_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoIsInShouldWork() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo in DEFAULT_DURACAO_TEMPO or UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.in=" + DEFAULT_DURACAO_TEMPO + "," + UPDATED_DURACAO_TEMPO);

        // Get all the registroDeEstudoList where duracaoTempo equals to UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.in=" + UPDATED_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo is not null
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.specified=true");

        // Get all the registroDeEstudoList where duracaoTempo is null
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoContainsSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo contains DEFAULT_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.contains=" + DEFAULT_DURACAO_TEMPO);

        // Get all the registroDeEstudoList where duracaoTempo contains UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.contains=" + UPDATED_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDuracaoTempoNotContainsSomething() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        // Get all the registroDeEstudoList where duracaoTempo does not contain DEFAULT_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldNotBeFound("duracaoTempo.doesNotContain=" + DEFAULT_DURACAO_TEMPO);

        // Get all the registroDeEstudoList where duracaoTempo does not contain UPDATED_DURACAO_TEMPO
        defaultRegistroDeEstudoShouldBeFound("duracaoTempo.doesNotContain=" + UPDATED_DURACAO_TEMPO);
    }


    @Test
    @Transactional
    public void getAllRegistroDeEstudosByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Area area = registroDeEstudo.getArea();
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);
        Long areaId = area.getId();

        // Get all the registroDeEstudoList where area equals to areaId
        defaultRegistroDeEstudoShouldBeFound("areaId.equals=" + areaId);

        // Get all the registroDeEstudoList where area equals to areaId + 1
        defaultRegistroDeEstudoShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllRegistroDeEstudosByDisciplinaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Disciplina disciplina = registroDeEstudo.getDisciplina();
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);
        Long disciplinaId = disciplina.getId();

        // Get all the registroDeEstudoList where disciplina equals to disciplinaId
        defaultRegistroDeEstudoShouldBeFound("disciplinaId.equals=" + disciplinaId);

        // Get all the registroDeEstudoList where disciplina equals to disciplinaId + 1
        defaultRegistroDeEstudoShouldNotBeFound("disciplinaId.equals=" + (disciplinaId + 1));
    }


    @Test
    @Transactional
    public void getAllRegistroDeEstudosByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = registroDeEstudo.getPessoa();
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);
        Long pessoaId = pessoa.getId();

        // Get all the registroDeEstudoList where pessoa equals to pessoaId
        defaultRegistroDeEstudoShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the registroDeEstudoList where pessoa equals to pessoaId + 1
        defaultRegistroDeEstudoShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegistroDeEstudoShouldBeFound(String filter) throws Exception {
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroDeEstudo.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicial").value(hasItem(DEFAULT_HORA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].duracaoTempo").value(hasItem(DEFAULT_DURACAO_TEMPO)));

        // Check, that the count call also returns 1
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegistroDeEstudoShouldNotBeFound(String filter) throws Exception {
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegistroDeEstudo() throws Exception {
        // Get the registroDeEstudo
        restRegistroDeEstudoMockMvc.perform(get("/api/registro-de-estudos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistroDeEstudo() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        int databaseSizeBeforeUpdate = registroDeEstudoRepository.findAll().size();

        // Update the registroDeEstudo
        RegistroDeEstudo updatedRegistroDeEstudo = registroDeEstudoRepository.findById(registroDeEstudo.getId()).get();
        // Disconnect from session so that the updates on updatedRegistroDeEstudo are not directly saved in db
        em.detach(updatedRegistroDeEstudo);
        updatedRegistroDeEstudo
            .horaInicial(UPDATED_HORA_INICIAL)
            .duracaoTempo(UPDATED_DURACAO_TEMPO);
        RegistroDeEstudoDTO registroDeEstudoDTO = registroDeEstudoMapper.toDto(updatedRegistroDeEstudo);

        restRegistroDeEstudoMockMvc.perform(put("/api/registro-de-estudos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registroDeEstudoDTO)))
            .andExpect(status().isOk());

        // Validate the RegistroDeEstudo in the database
        List<RegistroDeEstudo> registroDeEstudoList = registroDeEstudoRepository.findAll();
        assertThat(registroDeEstudoList).hasSize(databaseSizeBeforeUpdate);
        RegistroDeEstudo testRegistroDeEstudo = registroDeEstudoList.get(registroDeEstudoList.size() - 1);
        assertThat(testRegistroDeEstudo.getHoraInicial()).isEqualTo(UPDATED_HORA_INICIAL);
        assertThat(testRegistroDeEstudo.getDuracaoTempo()).isEqualTo(UPDATED_DURACAO_TEMPO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistroDeEstudo() throws Exception {
        int databaseSizeBeforeUpdate = registroDeEstudoRepository.findAll().size();

        // Create the RegistroDeEstudo
        RegistroDeEstudoDTO registroDeEstudoDTO = registroDeEstudoMapper.toDto(registroDeEstudo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroDeEstudoMockMvc.perform(put("/api/registro-de-estudos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(registroDeEstudoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroDeEstudo in the database
        List<RegistroDeEstudo> registroDeEstudoList = registroDeEstudoRepository.findAll();
        assertThat(registroDeEstudoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegistroDeEstudo() throws Exception {
        // Initialize the database
        registroDeEstudoRepository.saveAndFlush(registroDeEstudo);

        int databaseSizeBeforeDelete = registroDeEstudoRepository.findAll().size();

        // Delete the registroDeEstudo
        restRegistroDeEstudoMockMvc.perform(delete("/api/registro-de-estudos/{id}", registroDeEstudo.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroDeEstudo> registroDeEstudoList = registroDeEstudoRepository.findAll();
        assertThat(registroDeEstudoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
