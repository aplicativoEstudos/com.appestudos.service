package com.appestudos.service.web.rest;

import com.appestudos.service.AppestudosApp;
import com.appestudos.service.config.TestSecurityConfiguration;
import com.appestudos.service.domain.Area;
import com.appestudos.service.repository.AreaRepository;
import com.appestudos.service.service.AreaService;
import com.appestudos.service.service.dto.AreaDTO;
import com.appestudos.service.service.mapper.AreaMapper;
import com.appestudos.service.service.dto.AreaCriteria;
import com.appestudos.service.service.AreaQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AreaResource} REST controller.
 */
@SpringBootTest(classes = { AppestudosApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AreaResourceIT {

    private static final String DEFAULT_NOMA = "AAAAAAAAAA";
    private static final String UPDATED_NOMA = "BBBBBBBBBB";

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaQueryService areaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaMockMvc;

    private Area area;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createEntity(EntityManager em) {
        Area area = new Area()
            .noma(DEFAULT_NOMA);
        return area;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Area createUpdatedEntity(EntityManager em) {
        Area area = new Area()
            .noma(UPDATED_NOMA);
        return area;
    }

    @BeforeEach
    public void initTest() {
        area = createEntity(em);
    }

    @Test
    @Transactional
    public void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();
        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);
        restAreaMockMvc.perform(post("/api/areas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getNoma()).isEqualTo(DEFAULT_NOMA);
    }

    @Test
    @Transactional
    public void createAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area with an existing ID
        area.setId(1L);
        AreaDTO areaDTO = areaMapper.toDto(area);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaMockMvc.perform(post("/api/areas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].noma").value(hasItem(DEFAULT_NOMA)));
    }
    
    @Test
    @Transactional
    public void getArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(area.getId().intValue()))
            .andExpect(jsonPath("$.noma").value(DEFAULT_NOMA));
    }


    @Test
    @Transactional
    public void getAreasByIdFiltering() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        Long id = area.getId();

        defaultAreaShouldBeFound("id.equals=" + id);
        defaultAreaShouldNotBeFound("id.notEquals=" + id);

        defaultAreaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("id.greaterThan=" + id);

        defaultAreaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAreaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAreasByNomaIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma equals to DEFAULT_NOMA
        defaultAreaShouldBeFound("noma.equals=" + DEFAULT_NOMA);

        // Get all the areaList where noma equals to UPDATED_NOMA
        defaultAreaShouldNotBeFound("noma.equals=" + UPDATED_NOMA);
    }

    @Test
    @Transactional
    public void getAllAreasByNomaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma not equals to DEFAULT_NOMA
        defaultAreaShouldNotBeFound("noma.notEquals=" + DEFAULT_NOMA);

        // Get all the areaList where noma not equals to UPDATED_NOMA
        defaultAreaShouldBeFound("noma.notEquals=" + UPDATED_NOMA);
    }

    @Test
    @Transactional
    public void getAllAreasByNomaIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma in DEFAULT_NOMA or UPDATED_NOMA
        defaultAreaShouldBeFound("noma.in=" + DEFAULT_NOMA + "," + UPDATED_NOMA);

        // Get all the areaList where noma equals to UPDATED_NOMA
        defaultAreaShouldNotBeFound("noma.in=" + UPDATED_NOMA);
    }

    @Test
    @Transactional
    public void getAllAreasByNomaIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma is not null
        defaultAreaShouldBeFound("noma.specified=true");

        // Get all the areaList where noma is null
        defaultAreaShouldNotBeFound("noma.specified=false");
    }
                @Test
    @Transactional
    public void getAllAreasByNomaContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma contains DEFAULT_NOMA
        defaultAreaShouldBeFound("noma.contains=" + DEFAULT_NOMA);

        // Get all the areaList where noma contains UPDATED_NOMA
        defaultAreaShouldNotBeFound("noma.contains=" + UPDATED_NOMA);
    }

    @Test
    @Transactional
    public void getAllAreasByNomaNotContainsSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where noma does not contain DEFAULT_NOMA
        defaultAreaShouldNotBeFound("noma.doesNotContain=" + DEFAULT_NOMA);

        // Get all the areaList where noma does not contain UPDATED_NOMA
        defaultAreaShouldBeFound("noma.doesNotContain=" + UPDATED_NOMA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAreaShouldBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].noma").value(hasItem(DEFAULT_NOMA)));

        // Check, that the count call also returns 1
        restAreaMockMvc.perform(get("/api/areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAreaShouldNotBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAreaMockMvc.perform(get("/api/areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = areaRepository.findById(area.getId()).get();
        // Disconnect from session so that the updates on updatedArea are not directly saved in db
        em.detach(updatedArea);
        updatedArea
            .noma(UPDATED_NOMA);
        AreaDTO areaDTO = areaMapper.toDto(updatedArea);

        restAreaMockMvc.perform(put("/api/areas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getNoma()).isEqualTo(UPDATED_NOMA);
    }

    @Test
    @Transactional
    public void updateNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaMockMvc.perform(put("/api/areas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Delete the area
        restAreaMockMvc.perform(delete("/api/areas/{id}", area.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
