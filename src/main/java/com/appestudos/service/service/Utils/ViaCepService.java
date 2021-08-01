package com.appestudos.service.service.Utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appestudos.service.service.dto.EnderecoViaCepDto;


@FeignClient(value="viaCep", url = "http://viacep.com.br/ws")
public interface ViaCepService {
	
//	@RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
	 @GetMapping("/{cep}/json/")
	ResponseEntity<EnderecoViaCepDto> enviarEmail(@PathVariable("cep") String cep);
}
