package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.dto.ResponseDTO;
import br.com.eduardo.dto.ResultadoVotoDTO;
import br.com.eduardo.dto.VotoDTO;
import br.com.eduardo.service.impl.VotacaoService;

@RestController
@RequestMapping("/v1/votacao")
public class VotacaoController {

	@Autowired
	private VotacaoService votacaoService;
		
	@PostMapping
	public ResponseEntity<?> votar(@RequestBody VotoDTO dto) {
		votacaoService.votar(dto);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO("Votação recebida com successo"), HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> resultadoVotacao(@PathVariable(value = "id") long id) {
		
		ResultadoVotoDTO o = votacaoService.resultadoVotacao(id);
		return new ResponseEntity<>(o, HttpStatus.OK);
		
	}

}
