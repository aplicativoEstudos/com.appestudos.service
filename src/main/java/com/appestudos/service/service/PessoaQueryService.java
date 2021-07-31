package com.appestudos.service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.UUIDFilter;

import com.appestudos.service.domain.Pessoa;
import com.appestudos.service.domain.*; // for static metamodels
import com.appestudos.service.repository.PessoaRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.PessoaCriteria;
import com.appestudos.service.service.dto.PessoaDTO;
import com.appestudos.service.service.mapper.PessoaMapper;

/**
 * Service for executing complex queries for {@link Pessoa} entities in the database.
 * The main input is a {@link PessoaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PessoaDTO} or a {@link Page} of {@link PessoaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PessoaQueryService extends QueryService<Pessoa> {

    private final Logger log = LoggerFactory.getLogger(PessoaQueryService.class);

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private static final String SUB = "id_user";
    
    public PessoaQueryService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    /**
     * Return a {@link List} of {@link PessoaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PessoaDTO> findByCriteria(PessoaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaMapper.toDto(pessoaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PessoaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaDTO> findByCriteria(PessoaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setIdUser(new UUIDFilter());
        criteria.getIdUser().setEquals(UUID.fromString(((Optional<Map<String, String>>)SecurityUtils.getCurrentLoginMatricula()).get().get(SUB)));
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaRepository.findAll(specification, page)
            .map(pessoaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PessoaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pessoa> specification = createSpecification(criteria);
        return pessoaRepository.count(specification);
    }

    /**
     * Function to convert {@link PessoaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pessoa> createSpecification(PessoaCriteria criteria) {
        Specification<Pessoa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pessoa_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Pessoa_.nome));
            }
            if (criteria.getSobrenome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSobrenome(), Pessoa_.sobrenome));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Pessoa_.email));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Pessoa_.telefone));
            }
            if (criteria.getIdUser() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUser(), Pessoa_.idUser));
            }
            if (criteria.getEnderecoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnderecoId(),
                    root -> root.join(Pessoa_.endereco, JoinType.LEFT).get(Endereco_.id)));
            }
        }
        return specification;
    }
}
