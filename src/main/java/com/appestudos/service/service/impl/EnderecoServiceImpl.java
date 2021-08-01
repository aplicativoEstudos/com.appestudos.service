package com.appestudos.service.service.impl;

import com.appestudos.service.service.EnderecoService;
import com.appestudos.service.service.Utils.MailService;
import com.appestudos.service.domain.Endereco;
import com.appestudos.service.repository.EnderecoRepository;
import com.appestudos.service.security.SecurityUtils;
import com.appestudos.service.service.dto.EnderecoDTO;
import com.appestudos.service.service.dto.EnderecoViaCepDto;
import com.appestudos.service.service.mapper.EnderecoMapper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;
    
    private static final String SUB = "id_user";
    
    @Autowired 
    private MailService mailService;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco.setIdUser(UUID.fromString(((Optional<Map<String, String>>)SecurityUtils.getCurrentLoginMatricula()).get().get(SUB)));
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id)
            .map(enderecoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EnderecoViaCepDto viaCep(String cep) {
    	EnderecoViaCepDto test = mailService.enviarEmail(cep).getBody();
    	return test;
    }
//    	EnderecoViaCepDto enderecoDto = new EnderecoViaCepDto();
//    	
//    	String url = "http://viacep.com.br/ws/" + cep + "/json/";
//    	//Ler Json a partir da URL
//    	try {
//    		InputStream is = new URL(url).openStream();
//    		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//    		StringBuilder sb = new StringBuilder();
//    		int cp;
//    		while ((cp = rd.read()) != -1) {
//    			sb.append((char) cp);
//    		}
//    		String jsonText = sb.toString();
//    		Gson gson = new Gson();
//    		enderecoDto = gson.fromJson(jsonText, EnderecoViaCepDto.class);
//    	}catch (Exception e1) {
//    		e1.printStackTrace();
//    		throw new RuntimeException("Erro na consulta ZIP COD "+cep);
//    	}
//    	return enderecoDto;
//    }
}
