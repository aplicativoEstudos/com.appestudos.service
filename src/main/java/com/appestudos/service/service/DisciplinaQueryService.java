package com.appestudos.service.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.appestudos.service.domain.Disciplina;
import com.appestudos.service.domain.*; // for static metamodels
import com.appestudos.service.repository.DisciplinaRepository;
import com.appestudos.service.service.dto.DisciplinaCriteria;
import com.appestudos.service.service.dto.DisciplinaDTO;
import com.appestudos.service.service.mapper.DisciplinaMapper;

/**
 * Service for executing complex queries for {@link Disciplina} entities in the database.
 * The main input is a {@link DisciplinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisciplinaDTO} or a {@link Page} of {@link DisciplinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisciplinaQueryService extends QueryService<Disciplina> {

    private final Logger log = LoggerFactory.getLogger(DisciplinaQueryService.class);

    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaMapper disciplinaMapper;

    public DisciplinaQueryService(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
    }

    /**
     * Return a {@link List} of {@link DisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisciplinaDTO> findByCriteria(DisciplinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Disciplina> specification = createSpecification(criteria);
        return disciplinaMapper.toDto(disciplinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisciplinaDTO> findByCriteria(DisciplinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Disciplina> specification = createSpecification(criteria);
        return disciplinaRepository.findAll(specification, page)
            .map(disciplinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisciplinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Disciplina> specification = createSpecification(criteria);
        return disciplinaRepository.count(specification);
    }

    /**
     * Function to convert {@link DisciplinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Disciplina> createSpecification(DisciplinaCriteria criteria) {
        Specification<Disciplina> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Disciplina_.id));
            }
            if (criteria.getNomeDisciplina() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeDisciplina(), Disciplina_.nomeDisciplina));
            }
        }
        return specification;
    }
}
