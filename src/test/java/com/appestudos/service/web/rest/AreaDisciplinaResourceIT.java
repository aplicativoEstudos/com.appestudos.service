package com.appestudos.service.web.rest;

import com.appestudos.service.AppestudosApp;
import com.appestudos.service.config.TestSecurityConfiguration;
import com.appestudos.service.domain.AreaDisciplina;
import com.appestudos.service.domain.Area;
import com.appestudos.service.domain.Disciplina;
import com.appestudos.service.repository.AreaDisciplinaRepository;
import com.appestudos.service.service.AreaDisciplinaService;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;
import com.appestudos.service.service.mapper.AreaDisciplinaMapper;
import com.appestudos.service.service.dto.AreaDisciplinaCriteria;
import com.appestudos.service.service.AreaDisciplinaQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AreaDisciplinaResource} REST controller.
 */
@SpringBootTest(classes = { AppestudosApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AreaDisciplinaResourceIT {

    private static final Boolean DEFAULT_GERAL = false;
    private static final Boolean UPDATED_GERAL = true;

    private static final UUID DEFAULT_ID_USER = UUID.randomUUID();
    private static final UUID UPDATED_ID_USER = UUID.randomUUID();

    @Autowired
    private AreaDisciplinaRepository areaDisciplinaRepository;

    @Autowired
    private AreaDisciplinaMapper areaDisciplinaMapper;

    @Autowired
    private AreaDisciplinaService areaDisciplinaService;

    @Autowired
    private AreaDisciplinaQueryService areaDisciplinaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaDisciplinaMockMvc;

    private AreaDisciplina areaDisciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaDisciplina createEntity(EntityManager em) {
        AreaDisciplina areaDisciplina = new AreaDisciplina()
            .geral(DEFAULT_GERAL)
            .idUser(DEFAULT_ID_USER);
        return areaDisciplina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaDisciplina createUpdatedEntity(EntityManager em) {
        AreaDisciplina areaDisciplina = new AreaDisciplina()
            .geral(UPDATED_GERAL)
            .idUser(UPDATED_ID_USER);
        return areaDisciplina;
    }

    @BeforeEach
    public void initTest() {
        areaDisciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaDisciplina() throws Exception {
        int databaseSizeBeforeCreate = areaDisciplinaRepository.findAll().size();
        // Create the AreaDisciplina
        AreaDisciplinaDTO areaDisciplinaDTO = areaDisciplinaMapper.toDto(areaDisciplina);
        restAreaDisciplinaMockMvc.perform(post("/api/area-disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDisciplinaDTO)))
            .andExpect(status().isCreated());

        // Validate the AreaDisciplina in the database
        List<AreaDisciplina> areaDisciplinaList = areaDisciplinaRepository.findAll();
        assertThat(areaDisciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        AreaDisciplina testAreaDisciplina = areaDisciplinaList.get(areaDisciplinaList.size() - 1);
        assertThat(testAreaDisciplina.isGeral()).isEqualTo(DEFAULT_GERAL);
        assertThat(testAreaDisciplina.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    public void createAreaDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaDisciplinaRepository.findAll().size();

        // Create the AreaDisciplina with an existing ID
        areaDisciplina.setId(1L);
        AreaDisciplinaDTO areaDisciplinaDTO = areaDisciplinaMapper.toDto(areaDisciplina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaDisciplinaMockMvc.perform(post("/api/area-disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDisciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaDisciplina in the database
        List<AreaDisciplina> areaDisciplinaList = areaDisciplinaRepository.findAll();
        assertThat(areaDisciplinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAreaDisciplinas() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].geral").value(hasItem(DEFAULT_GERAL.booleanValue())))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));
    }
    
    @Test
    @Transactional
    public void getAreaDisciplina() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get the areaDisciplina
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas/{id}", areaDisciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaDisciplina.getId().intValue()))
            .andExpect(jsonPath("$.geral").value(DEFAULT_GERAL.booleanValue()))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER.toString()));
    }


    @Test
    @Transactional
    public void getAreaDisciplinasByIdFiltering() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        Long id = areaDisciplina.getId();

        defaultAreaDisciplinaShouldBeFound("id.equals=" + id);
        defaultAreaDisciplinaShouldNotBeFound("id.notEquals=" + id);

        defaultAreaDisciplinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAreaDisciplinaShouldNotBeFound("id.greaterThan=" + id);

        defaultAreaDisciplinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAreaDisciplinaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAreaDisciplinasByGeralIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where geral equals to DEFAULT_GERAL
        defaultAreaDisciplinaShouldBeFound("geral.equals=" + DEFAULT_GERAL);

        // Get all the areaDisciplinaList where geral equals to UPDATED_GERAL
        defaultAreaDisciplinaShouldNotBeFound("geral.equals=" + UPDATED_GERAL);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByGeralIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where geral not equals to DEFAULT_GERAL
        defaultAreaDisciplinaShouldNotBeFound("geral.notEquals=" + DEFAULT_GERAL);

        // Get all the areaDisciplinaList where geral not equals to UPDATED_GERAL
        defaultAreaDisciplinaShouldBeFound("geral.notEquals=" + UPDATED_GERAL);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByGeralIsInShouldWork() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where geral in DEFAULT_GERAL or UPDATED_GERAL
        defaultAreaDisciplinaShouldBeFound("geral.in=" + DEFAULT_GERAL + "," + UPDATED_GERAL);

        // Get all the areaDisciplinaList where geral equals to UPDATED_GERAL
        defaultAreaDisciplinaShouldNotBeFound("geral.in=" + UPDATED_GERAL);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByGeralIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where geral is not null
        defaultAreaDisciplinaShouldBeFound("geral.specified=true");

        // Get all the areaDisciplinaList where geral is null
        defaultAreaDisciplinaShouldNotBeFound("geral.specified=false");
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where idUser equals to DEFAULT_ID_USER
        defaultAreaDisciplinaShouldBeFound("idUser.equals=" + DEFAULT_ID_USER);

        // Get all the areaDisciplinaList where idUser equals to UPDATED_ID_USER
        defaultAreaDisciplinaShouldNotBeFound("idUser.equals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByIdUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where idUser not equals to DEFAULT_ID_USER
        defaultAreaDisciplinaShouldNotBeFound("idUser.notEquals=" + DEFAULT_ID_USER);

        // Get all the areaDisciplinaList where idUser not equals to UPDATED_ID_USER
        defaultAreaDisciplinaShouldBeFound("idUser.notEquals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByIdUserIsInShouldWork() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where idUser in DEFAULT_ID_USER or UPDATED_ID_USER
        defaultAreaDisciplinaShouldBeFound("idUser.in=" + DEFAULT_ID_USER + "," + UPDATED_ID_USER);

        // Get all the areaDisciplinaList where idUser equals to UPDATED_ID_USER
        defaultAreaDisciplinaShouldNotBeFound("idUser.in=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByIdUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        // Get all the areaDisciplinaList where idUser is not null
        defaultAreaDisciplinaShouldBeFound("idUser.specified=true");

        // Get all the areaDisciplinaList where idUser is null
        defaultAreaDisciplinaShouldNotBeFound("idUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllAreaDisciplinasByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);
        Area area = AreaResourceIT.createEntity(em);
        em.persist(area);
        em.flush();
        areaDisciplina.setArea(area);
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);
        Long areaId = area.getId();

        // Get all the areaDisciplinaList where area equals to areaId
        defaultAreaDisciplinaShouldBeFound("areaId.equals=" + areaId);

        // Get all the areaDisciplinaList where area equals to areaId + 1
        defaultAreaDisciplinaShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllAreaDisciplinasByDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);
        Disciplina disciplina = DisciplinaResourceIT.createEntity(em);
        em.persist(disciplina);
        em.flush();
        areaDisciplina.setDisciplina(disciplina);
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);
        Long disciplinaId = disciplina.getId();

        // Get all the areaDisciplinaList where disciplina equals to disciplinaId
        defaultAreaDisciplinaShouldBeFound("disciplinaId.equals=" + disciplinaId);

        // Get all the areaDisciplinaList where disciplina equals to disciplinaId + 1
        defaultAreaDisciplinaShouldNotBeFound("disciplinaId.equals=" + (disciplinaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAreaDisciplinaShouldBeFound(String filter) throws Exception {
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaDisciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].geral").value(hasItem(DEFAULT_GERAL.booleanValue())))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));

        // Check, that the count call also returns 1
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAreaDisciplinaShouldNotBeFound(String filter) throws Exception {
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAreaDisciplina() throws Exception {
        // Get the areaDisciplina
        restAreaDisciplinaMockMvc.perform(get("/api/area-disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaDisciplina() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        int databaseSizeBeforeUpdate = areaDisciplinaRepository.findAll().size();

        // Update the areaDisciplina
        AreaDisciplina updatedAreaDisciplina = areaDisciplinaRepository.findById(areaDisciplina.getId()).get();
        // Disconnect from session so that the updates on updatedAreaDisciplina are not directly saved in db
        em.detach(updatedAreaDisciplina);
        updatedAreaDisciplina
            .geral(UPDATED_GERAL)
            .idUser(UPDATED_ID_USER);
        AreaDisciplinaDTO areaDisciplinaDTO = areaDisciplinaMapper.toDto(updatedAreaDisciplina);

        restAreaDisciplinaMockMvc.perform(put("/api/area-disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDisciplinaDTO)))
            .andExpect(status().isOk());

        // Validate the AreaDisciplina in the database
        List<AreaDisciplina> areaDisciplinaList = areaDisciplinaRepository.findAll();
        assertThat(areaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
        AreaDisciplina testAreaDisciplina = areaDisciplinaList.get(areaDisciplinaList.size() - 1);
        assertThat(testAreaDisciplina.isGeral()).isEqualTo(UPDATED_GERAL);
        assertThat(testAreaDisciplina.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = areaDisciplinaRepository.findAll().size();

        // Create the AreaDisciplina
        AreaDisciplinaDTO areaDisciplinaDTO = areaDisciplinaMapper.toDto(areaDisciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaDisciplinaMockMvc.perform(put("/api/area-disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDisciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaDisciplina in the database
        List<AreaDisciplina> areaDisciplinaList = areaDisciplinaRepository.findAll();
        assertThat(areaDisciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAreaDisciplina() throws Exception {
        // Initialize the database
        areaDisciplinaRepository.saveAndFlush(areaDisciplina);

        int databaseSizeBeforeDelete = areaDisciplinaRepository.findAll().size();

        // Delete the areaDisciplina
        restAreaDisciplinaMockMvc.perform(delete("/api/area-disciplinas/{id}", areaDisciplina.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AreaDisciplina> areaDisciplinaList = areaDisciplinaRepository.findAll();
        assertThat(areaDisciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
