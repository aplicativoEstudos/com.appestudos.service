package com.appestudos.service.web.rest;

import com.appestudos.service.AppestudosApp;
import com.appestudos.service.config.TestSecurityConfiguration;
import com.appestudos.service.domain.Pessoa;
import com.appestudos.service.domain.Endereco;
import com.appestudos.service.repository.PessoaRepository;
import com.appestudos.service.service.PessoaService;
import com.appestudos.service.service.dto.PessoaDTO;
import com.appestudos.service.service.mapper.PessoaMapper;
import com.appestudos.service.service.dto.PessoaCriteria;
import com.appestudos.service.service.PessoaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@SpringBootTest(classes = { AppestudosApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PessoaResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "ui'0=o@ukP";
    private static final String UPDATED_EMAIL = "Lz$$'E@v";

    private static final String DEFAULT_TELEFONE = "(60) 1717-7284";
    private static final String UPDATED_TELEFONE = "(22) 7164-4661";

    private static final UUID DEFAULT_ID_USER = UUID.randomUUID();
    private static final UUID UPDATED_ID_USER = UUID.randomUUID();

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaQueryService pessoaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .idUser(DEFAULT_ID_USER);
        return pessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .idUser(UPDATED_ID_USER);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();
        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPessoa.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPessoa.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testPessoa.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobrenomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setSobrenome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setEmail(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));
    }
    
    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER.toString()));
    }


    @Test
    @Transactional
    public void getPessoasByIdFiltering() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        Long id = pessoa.getId();

        defaultPessoaShouldBeFound("id.equals=" + id);
        defaultPessoaShouldNotBeFound("id.notEquals=" + id);

        defaultPessoaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPessoaShouldNotBeFound("id.greaterThan=" + id);

        defaultPessoaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPessoaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPessoasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome equals to DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome not equals to DEFAULT_NOME
        defaultPessoaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome not equals to UPDATED_NOME
        defaultPessoaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPessoaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome is not null
        defaultPessoaShouldBeFound("nome.specified=true");

        // Get all the pessoaList where nome is null
        defaultPessoaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByNomeContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome contains DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the pessoaList where nome contains UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome does not contain DEFAULT_NOME
        defaultPessoaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the pessoaList where nome does not contain UPDATED_NOME
        defaultPessoaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllPessoasBySobrenomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome equals to DEFAULT_SOBRENOME
        defaultPessoaShouldBeFound("sobrenome.equals=" + DEFAULT_SOBRENOME);

        // Get all the pessoaList where sobrenome equals to UPDATED_SOBRENOME
        defaultPessoaShouldNotBeFound("sobrenome.equals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllPessoasBySobrenomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome not equals to DEFAULT_SOBRENOME
        defaultPessoaShouldNotBeFound("sobrenome.notEquals=" + DEFAULT_SOBRENOME);

        // Get all the pessoaList where sobrenome not equals to UPDATED_SOBRENOME
        defaultPessoaShouldBeFound("sobrenome.notEquals=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllPessoasBySobrenomeIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome in DEFAULT_SOBRENOME or UPDATED_SOBRENOME
        defaultPessoaShouldBeFound("sobrenome.in=" + DEFAULT_SOBRENOME + "," + UPDATED_SOBRENOME);

        // Get all the pessoaList where sobrenome equals to UPDATED_SOBRENOME
        defaultPessoaShouldNotBeFound("sobrenome.in=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllPessoasBySobrenomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome is not null
        defaultPessoaShouldBeFound("sobrenome.specified=true");

        // Get all the pessoaList where sobrenome is null
        defaultPessoaShouldNotBeFound("sobrenome.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasBySobrenomeContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome contains DEFAULT_SOBRENOME
        defaultPessoaShouldBeFound("sobrenome.contains=" + DEFAULT_SOBRENOME);

        // Get all the pessoaList where sobrenome contains UPDATED_SOBRENOME
        defaultPessoaShouldNotBeFound("sobrenome.contains=" + UPDATED_SOBRENOME);
    }

    @Test
    @Transactional
    public void getAllPessoasBySobrenomeNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sobrenome does not contain DEFAULT_SOBRENOME
        defaultPessoaShouldNotBeFound("sobrenome.doesNotContain=" + DEFAULT_SOBRENOME);

        // Get all the pessoaList where sobrenome does not contain UPDATED_SOBRENOME
        defaultPessoaShouldBeFound("sobrenome.doesNotContain=" + UPDATED_SOBRENOME);
    }


    @Test
    @Transactional
    public void getAllPessoasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email equals to DEFAULT_EMAIL
        defaultPessoaShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the pessoaList where email equals to UPDATED_EMAIL
        defaultPessoaShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email not equals to DEFAULT_EMAIL
        defaultPessoaShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the pessoaList where email not equals to UPDATED_EMAIL
        defaultPessoaShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPessoaShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the pessoaList where email equals to UPDATED_EMAIL
        defaultPessoaShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email is not null
        defaultPessoaShouldBeFound("email.specified=true");

        // Get all the pessoaList where email is null
        defaultPessoaShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByEmailContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email contains DEFAULT_EMAIL
        defaultPessoaShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the pessoaList where email contains UPDATED_EMAIL
        defaultPessoaShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPessoasByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where email does not contain DEFAULT_EMAIL
        defaultPessoaShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the pessoaList where email does not contain UPDATED_EMAIL
        defaultPessoaShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPessoasByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone equals to DEFAULT_TELEFONE
        defaultPessoaShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the pessoaList where telefone equals to UPDATED_TELEFONE
        defaultPessoaShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllPessoasByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone not equals to DEFAULT_TELEFONE
        defaultPessoaShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the pessoaList where telefone not equals to UPDATED_TELEFONE
        defaultPessoaShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllPessoasByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultPessoaShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the pessoaList where telefone equals to UPDATED_TELEFONE
        defaultPessoaShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllPessoasByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone is not null
        defaultPessoaShouldBeFound("telefone.specified=true");

        // Get all the pessoaList where telefone is null
        defaultPessoaShouldNotBeFound("telefone.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone contains DEFAULT_TELEFONE
        defaultPessoaShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the pessoaList where telefone contains UPDATED_TELEFONE
        defaultPessoaShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllPessoasByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where telefone does not contain DEFAULT_TELEFONE
        defaultPessoaShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the pessoaList where telefone does not contain UPDATED_TELEFONE
        defaultPessoaShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }


    @Test
    @Transactional
    public void getAllPessoasByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where idUser equals to DEFAULT_ID_USER
        defaultPessoaShouldBeFound("idUser.equals=" + DEFAULT_ID_USER);

        // Get all the pessoaList where idUser equals to UPDATED_ID_USER
        defaultPessoaShouldNotBeFound("idUser.equals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllPessoasByIdUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where idUser not equals to DEFAULT_ID_USER
        defaultPessoaShouldNotBeFound("idUser.notEquals=" + DEFAULT_ID_USER);

        // Get all the pessoaList where idUser not equals to UPDATED_ID_USER
        defaultPessoaShouldBeFound("idUser.notEquals=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllPessoasByIdUserIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where idUser in DEFAULT_ID_USER or UPDATED_ID_USER
        defaultPessoaShouldBeFound("idUser.in=" + DEFAULT_ID_USER + "," + UPDATED_ID_USER);

        // Get all the pessoaList where idUser equals to UPDATED_ID_USER
        defaultPessoaShouldNotBeFound("idUser.in=" + UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void getAllPessoasByIdUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where idUser is not null
        defaultPessoaShouldBeFound("idUser.specified=true");

        // Get all the pessoaList where idUser is null
        defaultPessoaShouldNotBeFound("idUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllPessoasByEnderecoIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        Endereco endereco = EnderecoResourceIT.createEntity(em);
        em.persist(endereco);
        em.flush();
        pessoa.setEndereco(endereco);
        pessoaRepository.saveAndFlush(pessoa);
        Long enderecoId = endereco.getId();

        // Get all the pessoaList where endereco equals to enderecoId
        defaultPessoaShouldBeFound("enderecoId.equals=" + enderecoId);

        // Get all the pessoaList where endereco equals to enderecoId + 1
        defaultPessoaShouldNotBeFound("enderecoId.equals=" + (enderecoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPessoaShouldBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.toString())));

        // Check, that the count call also returns 1
        restPessoaMockMvc.perform(get("/api/pessoas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPessoaShouldNotBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPessoaMockMvc.perform(get("/api/pessoas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .idUser(UPDATED_ID_USER);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPessoa.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testPessoa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPessoa.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testPessoa.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc.perform(put("/api/pessoas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Delete the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
