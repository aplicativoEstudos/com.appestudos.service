package com.appestudos.service.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appestudos.service.domain.Pessoa;
import com.appestudos.service.domain.RegistroDeEstudo;
import com.appestudos.service.repository.PessoaRepository;
import com.appestudos.service.repository.RegistroDeEstudoRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.PessoaQueryService;
import com.appestudos.service.service.RegistroDeEstudoService;
import com.appestudos.service.service.dto.PessoaCriteria;
import com.appestudos.service.service.dto.PessoaDTO;
import com.appestudos.service.service.dto.RegistroDeEstudoDTO;
import com.appestudos.service.service.mapper.PessoaMapper;
import com.appestudos.service.service.mapper.RegistroDeEstudoMapper;

import io.github.jhipster.service.filter.UUIDFilter;

/**
 * Service Implementation for managing {@link RegistroDeEstudo}.
 */
@Service
@Transactional
public class RegistroDeEstudoServiceImpl implements RegistroDeEstudoService {
	
	private static final String SOBRE_NOME = "sobre_nome";

    private static final String SUB = "id_user";
    
	private static final String PREFERRED_USERNAME = "preferred_username";
	
	private static final String NAME = "name";

	private final Logger log = LoggerFactory.getLogger(RegistroDeEstudoServiceImpl.class);

    private final RegistroDeEstudoRepository registroDeEstudoRepository;
    
	private final PessoaQueryService pessoaQueryService;
	
	private final PessoaRepository pessoaRepository;
	
	private final PessoaMapper pessoaMapper;

    private final RegistroDeEstudoMapper registroDeEstudoMapper;
    
    public RegistroDeEstudoServiceImpl(RegistroDeEstudoRepository registroDeEstudoRepository, RegistroDeEstudoMapper registroDeEstudoMapper,
    		PessoaQueryService pessoaQueryService, PessoaMapper pessoaMapper,
    		PessoaRepository pessoaRepository) {
        this.registroDeEstudoRepository = registroDeEstudoRepository;
        this.registroDeEstudoMapper = registroDeEstudoMapper;
        this.pessoaQueryService = pessoaQueryService;
        this.pessoaMapper = pessoaMapper;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public RegistroDeEstudoDTO save(RegistroDeEstudoDTO registroDeEstudoDTO) {
        log.debug("Request to save RegistroDeEstudo : {}", registroDeEstudoDTO);
        if(registroDeEstudoDTO.getHoraInicial()==null) {
        	registroDeEstudoDTO.setHoraInicial(Instant.now());
        }
        RegistroDeEstudo registroDeEstudo = registroDeEstudoMapper.toEntity(registroDeEstudoDTO);
        registroDeEstudo.setPessoa(pessoaLogada());
        registroDeEstudo = registroDeEstudoRepository.save(registroDeEstudo);
        return registroDeEstudoMapper.toDto(registroDeEstudo);
    }
    
//    @Override
//    public RegistroDeEstudoDTO start(RegistroDeEstudoDTO registroDeEstudoDTO) {
//        log.debug("Request to save RegistroDeEstudo : {}", registroDeEstudoDTO);
//        
//        if(registroDeEstudoDTO.getHoraInicial()==null) {
//        	registroDeEstudoDTO.setHoraInicial(Instant.now());
//        }
//        registroDeEstudoDTO.setDuracaoTempo(null);
//        RegistroDeEstudo registroDeEstudo = registroDeEstudoMapper.toEntity(registroDeEstudoDTO);
//        registroDeEstudo.setPessoa(pessoaLogada());
//        registroDeEstudo = registroDeEstudoRepository.save(registroDeEstudo);
//        return registroDeEstudoMapper.toDto(registroDeEstudo);
//    }
//    
//    @Override
//    public RegistroDeEstudoDTO stop(RegistroDeEstudoDTO registroDeEstudoDTO) {
//        log.debug("Request to save RegistroDeEstudo : {}", registroDeEstudoDTO);
//        Duration duration = Duration.between(registroDeEstudoDTO.getHoraInicial(),registroDeEstudoDTO.getHoraFinal());
//        registroDeEstudoDTO.setDuracaoTempo(String.format(duration.toHours()+":"+duration.toMinutesPart()+
//                ":"+duration.toSecondsPart()));
//        RegistroDeEstudo registroDeEstudo = registroDeEstudoMapper.toEntity(registroDeEstudoDTO);
//        registroDeEstudo = registroDeEstudoRepository.save(registroDeEstudo);
//        return registroDeEstudoMapper.toDto(registroDeEstudo);
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegistroDeEstudoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroDeEstudos");
        return registroDeEstudoRepository.findAll(pageable)
            .map(registroDeEstudoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroDeEstudoDTO> findOne(Long id) {
        log.debug("Request to get RegistroDeEstudo : {}", id);
        return registroDeEstudoRepository.findById(id)
            .map(registroDeEstudoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistroDeEstudo : {}", id);
        registroDeEstudoRepository.deleteById(id);
    }
    
	private Pessoa pessoaLogada() {
		//        Usuario logado
        Optional<Map<String, String>> currentLoginMatricula = SecurityUtils.getCurrentLoginMatricula();
        Pessoa pessoa = new Pessoa();
        Optional<Pessoa> pessoaOpt = pessoaRepository.findByIdUser(UUID.fromString(currentLoginMatricula.get().get(SUB)));
        if(pessoaOpt.isPresent()) {
        	pessoa = pessoaOpt.get();
        }else {
        	pessoa = new Pessoa();
        	pessoa.setEmail(currentLoginMatricula.get().get(PREFERRED_USERNAME));
        	pessoa.setNome(currentLoginMatricula.get().get(NAME));
        	pessoa.setSobrenome(currentLoginMatricula.get().get(SOBRE_NOME));
        	pessoa.setIdUser(UUID.fromString(currentLoginMatricula.get().get(SUB)));
        	pessoaRepository.save(pessoa);
        }
		return pessoa;
	}
}
