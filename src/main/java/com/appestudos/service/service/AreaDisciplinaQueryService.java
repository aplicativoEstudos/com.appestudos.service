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
import io.github.jhipster.service.filter.UUIDFilter;

import com.appestudos.service.domain.AreaDisciplina;
import com.appestudos.service.domain.*; // for static metamodels
import com.appestudos.service.repository.AreaDisciplinaRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.AreaDisciplinaCriteria;
import com.appestudos.service.service.dto.AreaDisciplinaDTO;
import com.appestudos.service.service.mapper.AreaDisciplinaMapper;

/**
 * Service for executing complex queries for {@link AreaDisciplina} entities in the database.
 * The main input is a {@link AreaDisciplinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AreaDisciplinaDTO} or a {@link Page} of {@link AreaDisciplinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AreaDisciplinaQueryService extends QueryService<AreaDisciplina> {

    private final Logger log = LoggerFactory.getLogger(AreaDisciplinaQueryService.class);

    private final AreaDisciplinaRepository areaDisciplinaRepository;

    private final AreaDisciplinaMapper areaDisciplinaMapper;
    
    private static final String SUB = "id_user";

    public AreaDisciplinaQueryService(AreaDisciplinaRepository areaDisciplinaRepository, AreaDisciplinaMapper areaDisciplinaMapper) {
        this.areaDisciplinaRepository = areaDisciplinaRepository;
        this.areaDisciplinaMapper = areaDisciplinaMapper;
    }

    /**
     * Return a {@link List} of {@link AreaDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AreaDisciplinaDTO> findByCriteria(AreaDisciplinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AreaDisciplina> specification = createSpecification(criteria);
        return areaDisciplinaMapper.toDto(areaDisciplinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AreaDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaDisciplinaDTO> findByCriteria(AreaDisciplinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setIdUser(new UUIDFilter());
        criteria.getIdUser().setEquals(UUID.fromString(((Optional<Map<String, String>>)SecurityUtils.getCurrentLoginMatricula()).get().get(SUB)));
        final Specification<AreaDisciplina> specification = createSpecification(criteria);
        return areaDisciplinaRepository.findAll(specification, page)
            .map(areaDisciplinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AreaDisciplinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AreaDisciplina> specification = createSpecification(criteria);
        return areaDisciplinaRepository.count(specification);
    }

    /**
     * Function to convert {@link AreaDisciplinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AreaDisciplina> createSpecification(AreaDisciplinaCriteria criteria) {
        Specification<AreaDisciplina> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AreaDisciplina_.id));
            }
            if (criteria.getGeral() != null) {
                specification = specification.and(buildSpecification(criteria.getGeral(), AreaDisciplina_.geral));
            }
            if (criteria.getIdUser() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUser(), AreaDisciplina_.idUser));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(AreaDisciplina_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getDisciplinaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisciplinaId(),
                    root -> root.join(AreaDisciplina_.disciplina, JoinType.LEFT).get(Disciplina_.id)));
            }
        }
        return specification;
    }
}
