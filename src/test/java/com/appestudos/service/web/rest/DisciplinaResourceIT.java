package com.appestudos.service.web.rest;

import com.appestudos.service.AppestudosApp;
import com.appestudos.service.config.TestSecurityConfiguration;
import com.appestudos.service.domain.Disciplina;
import com.appestudos.service.repository.DisciplinaRepository;
import com.appestudos.service.service.DisciplinaService;
import com.appestudos.service.service.dto.DisciplinaDTO;
import com.appestudos.service.service.mapper.DisciplinaMapper;
import com.appestudos.service.service.dto.DisciplinaCriteria;
import com.appestudos.service.service.DisciplinaQueryService;

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
 * Integration tests for the {@link DisciplinaResource} REST controller.
 */
@SpringBootTest(classes = { AppestudosApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DisciplinaResourceIT {

    private static final String DEFAULT_NOME_DISCIPLINA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DISCIPLINA = "BBBBBBBBBB";

    private static final UUID DEFAULT_ID_USER = UUID.randomUUID();
    private static final UUID UPDATED_ID_USER = UUID.randomUUID();

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private DisciplinaQueryService disciplinaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .nomeDisciplina(DEFAULT_NOME_DISCIPLINA)
            .idUser(DEFAULT_ID_USER);
        return disciplina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disciplina createUpdatedEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .nomeDisciplina(UPDATED_NOME_DISCIPLINA)
            .idUser(UPDATED_ID_USER);
        return disciplina;
    }

    @BeforeEach
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();
        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);
        restDisciplinaMockMvc.perform(post("/api/disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNomeDisciplina()).isEqualTo(DEFAULT_NOME_DISCIPLINA);
        assertThat(testDisciplina.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    public void createDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina with an existing ID
        disciplina.setId(1L);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaMockMvc.perform(post("/api/disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDisciplina").value(hasItem(DEFAULT_NOME_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));
    }
    
    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.nomeDisciplina").value(DEFAULT_NOME_DISCIPLINA))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER.toString()));
    }


    @Test
    @Transactional
    public void getDisciplinasByIdFiltering() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        Long id = disciplina.getId();

        defaultDisciplinaShouldBeFound("id.equals=" + id);
        defaultDisciplinaShouldNotBeFound("id.notEquals=" + id);

        defaultDisciplinaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisciplinaShouldNotBeFound("id.greaterThan=" + id);

        defaultDisciplinaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisciplinaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina equals to DEFAULT_NOME_DISCIPLINA
        defaultDisciplinaShouldBeFound("nomeDisciplina.equals=" + DEFAULT_NOME_DISCIPLINA);

        // Get all the disciplinaList where nomeDisciplina equals to UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.equals=" + UPDATED_NOME_DISCIPLINA);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina not equals to DEFAULT_NOME_DISCIPLINA
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.notEquals=" + DEFAULT_NOME_DISCIPLINA);

        // Get all the disciplinaList where nomeDisciplina not equals to UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldBeFound("nomeDisciplina.notEquals=" + UPDATED_NOME_DISCIPLINA);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina in DEFAULT_NOME_DISCIPLINA or UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldBeFound("nomeDisciplina.in=" + DEFAULT_NOME_DISCIPLINA + "," + UPDATED_NOME_DISCIPLINA);

        // Get all the disciplinaList where nomeDisciplina equals to UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.in=" + UPDATED_NOME_DISCIPLINA);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina is not null
        defaultDisciplinaShouldBeFound("nomeDisciplina.specified=true");

        // Get all the disciplinaList where nomeDisciplina is null
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaContainsSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina contains DEFAULT_NOME_DISCIPLINA
        defaultDisciplinaShouldBeFound("nomeDisciplina.contains=" + DEFAULT_NOME_DISCIPLINA);

        // Get all the disciplinaList where nomeDisciplina contains UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.contains=" + UPDATED_NOME_DISCIPLINA);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByNomeDisciplinaNotContainsSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where nomeDisciplina does not contain DEFAULT_NOME_DISCIPLINA
        defaultDisciplinaShouldNotBeFound("nomeDisciplina.doesNotContain=" + DEFAULT_NOME_DISCIPLINA);

        // Get all the disciplinaList where nomeDisciplina does not contain UPDATED_NOME_DISCIPLINA
        defaultDisciplinaShouldBeFound("nomeDisciplina.doesNotContain=" + UPDATED_NOME_DISCIPLINA);
    }


    @Test
    @Transactional
    public void getAllDisciplinasByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where idUser equals to DEFAULT_ID_USER
        defaultDisciplinaShouldBeFound("idUser.equals=" + DEFAULT_ID_USER);

        // Get all the disciplinaList where idUser equals to UPDATED_ID_USER
        defaultDisciplinaShouldNotBeFound("idUser.equals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByIdUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where idUser not equals to DEFAULT_ID_USER
        defaultDisciplinaShouldNotBeFound("idUser.notEquals=" + DEFAULT_ID_USER);

        // Get all the disciplinaList where idUser not equals to UPDATED_ID_USER
        defaultDisciplinaShouldBeFound("idUser.notEquals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByIdUserIsInShouldWork() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where idUser in DEFAULT_ID_USER or UPDATED_ID_USER
        defaultDisciplinaShouldBeFound("idUser.in=" + DEFAULT_ID_USER + "," + UPDATED_ID_USER);

        // Get all the disciplinaList where idUser equals to UPDATED_ID_USER
        defaultDisciplinaShouldNotBeFound("idUser.in=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllDisciplinasByIdUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList where idUser is not null
        defaultDisciplinaShouldBeFound("idUser.specified=true");

        // Get all the disciplinaList where idUser is null
        defaultDisciplinaShouldNotBeFound("idUser.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisciplinaShouldBeFound(String filter) throws Exception {
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDisciplina").value(hasItem(DEFAULT_NOME_DISCIPLINA)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));

        // Check, that the count call also returns 1
        restDisciplinaMockMvc.perform(get("/api/disciplinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisciplinaShouldNotBeFound(String filter) throws Exception {
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisciplinaMockMvc.perform(get("/api/disciplinas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findById(disciplina.getId()).get();
        // Disconnect from session so that the updates on updatedDisciplina are not directly saved in db
        em.detach(updatedDisciplina);
        updatedDisciplina
            .nomeDisciplina(UPDATED_NOME_DISCIPLINA)
            .idUser(UPDATED_ID_USER);
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(updatedDisciplina);

        restDisciplinaMockMvc.perform(put("/api/disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getNomeDisciplina()).isEqualTo(UPDATED_NOME_DISCIPLINA);
        assertThat(testDisciplina.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplinaMockMvc.perform(put("/api/disciplinas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplinaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Delete the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
