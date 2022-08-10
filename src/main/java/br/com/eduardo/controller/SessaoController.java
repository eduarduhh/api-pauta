package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.service.impl.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sessao")
@Api(value = "API de Pautas")

public class SessaoController {

	@Autowired
	private SessaoService sessaoService;
	

	@GetMapping
	@ApiOperation(value = "Listar de sessao")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(sessaoService.findAllSessaoDTO(), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Salvar sessão")
	public ResponseEntity<?> save(@RequestBody SessaoDTO sessaoDTO) {
		SessaoDTO salvarSessao = sessaoService.salvarSessao(sessaoDTO);
		return new ResponseEntity<SessaoDTO>(salvarSessao, HttpStatus.BAD_REQUEST);
	}

}
