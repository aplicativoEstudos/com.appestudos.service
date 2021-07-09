package com.appestudos.service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import io.github.jhipster.service.filter.StringFilter;

import com.appestudos.service.domain.*; // for static metamodels
import com.appestudos.service.repository.RegistroDeEstudoRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.PessoaCriteria;
import com.appestudos.service.service.dto.PessoaDTO;
import com.appestudos.service.service.dto.RegistroDeEstudoCriteria;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;
import com.appestudos.service.service.mapper.PessoaMapper;
import com.appestudos.service.service.mapper.RegistroDeEstudoMapper;

/**
 * Service for executing complex queries for {@link RegistroDeEstudo} entities in the database.
 * The main input is a {@link RegistroDeEstudoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistroDeEstudoDTO} or a {@link Page} of {@link RegistroDeEstudoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistroDeEstudoQueryService extends QueryService<RegistroDeEstudo> {

    private final Logger log = LoggerFactory.getLogger(RegistroDeEstudoQueryService.class);

    private final RegistroDeEstudoRepository registroDeEstudoRepository;

    private final PessoaQueryService pessoaQueryService;
	
	private final PessoaMapper pessoaMapper;
    
    private final RegistroDeEstudoMapper registroDeEstudoMapper;
    
    private static final String NAME = "name";

    public RegistroDeEstudoQueryService(RegistroDeEstudoRepository registroDeEstudoRepository, RegistroDeEstudoMapper registroDeEstudoMapper,
    		PessoaQueryService pessoaQueryService, PessoaMapper pessoaMapper) {
        this.registroDeEstudoRepository = registroDeEstudoRepository;
        this.registroDeEstudoMapper = registroDeEstudoMapper;
        this.pessoaQueryService = pessoaQueryService;
        this.pessoaMapper = pessoaMapper;
    }

    /**
     * Return a {@link List} of {@link RegistroDeEstudoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistroDeEstudoDTO> findByCriteria(RegistroDeEstudoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegistroDeEstudo> specification = createSpecification(criteria);
        return registroDeEstudoMapper.toDto(registroDeEstudoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegistroDeEstudoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroDeEstudoDTO> findByCriteria(RegistroDeEstudoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegistroDeEstudo> specification = createSpecification(criteria);
        return registroDeEstudoRepository.findAll(specification, page)
            .map(registroDeEstudoMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public Page<RegistroDeEstudoDTO> findByCriteriaLogado(RegistroDeEstudoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setPessoaId(new LongFilter());
        criteria.getPessoaId().setEquals(pessoaLogada().getId());
        final Specification<RegistroDeEstudo> specification = createSpecification(criteria);
        return registroDeEstudoRepository.findAll(specification, page)
            .map(registroDeEstudoMapper::toDto);
    }
    
	private Pessoa pessoaLogada() {
		//        Usuario logado
        Optional<Map<String, String>> currentLoginMatricula = SecurityUtils.getCurrentLoginMatricula();
        PessoaCriteria pessoaCriteria = new PessoaCriteria();
        pessoaCriteria.setNome(new StringFilter());
        pessoaCriteria.getNome().setEquals(currentLoginMatricula.get().get(NAME));
        Pessoa pessoa = new Pessoa();
        List<PessoaDTO> pessoasDto = pessoaQueryService.findByCriteria(pessoaCriteria);
        if(!pessoasDto.isEmpty()) {
        	pessoa = pessoaMapper.toEntity(pessoasDto.get(0));
        }else {
        	throw new RuntimeException("Não existe pessoa para esse usuário");
        }
		return pessoa;
	}

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegistroDeEstudoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegistroDeEstudo> specification = createSpecification(criteria);
        return registroDeEstudoRepository.count(specification);
    }

    /**
     * Function to convert {@link RegistroDeEstudoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegistroDeEstudo> createSpecification(RegistroDeEstudoCriteria criteria) {
        Specification<RegistroDeEstudo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegistroDeEstudo_.id));
            }
            if (criteria.getHoraInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoraInicial(), RegistroDeEstudo_.horaInicial));
            }
            if (criteria.getHoraFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoraFinal(), RegistroDeEstudo_.horaFinal));
            }
            if (criteria.getDuracaoTempo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuracaoTempo(), RegistroDeEstudo_.duracaoTempo));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(RegistroDeEstudo_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getDisciplinaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisciplinaId(),
                    root -> root.join(RegistroDeEstudo_.disciplina, JoinType.LEFT).get(Disciplina_.id)));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPessoaId(),
                    root -> root.join(RegistroDeEstudo_.pessoa, JoinType.LEFT).get(Pessoa_.id)));
            }
        }
        return specification;
    }
}
