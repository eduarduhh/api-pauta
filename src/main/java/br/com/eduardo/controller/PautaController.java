package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.service.impl.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/pauta")
@Api(value = "API de Pautas")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@GetMapping
	@ApiOperation(value = "Listagem de pauta")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(pautaService.findAllByIndicExclusao(false), HttpStatus.OK);

	}

	@PostMapping
	@ApiOperation(value = "Salvar pauta")
	public ResponseEntity<?> save(@RequestBody Pauta pauta) {

		pautaService.cadaastrado(pauta.getDescricao());

		pauta.setId(null);
		pauta.setIndicExclusao(false);
		Pauta save = pautaService.save(pauta);

		return new ResponseEntity<Pauta>(save, HttpStatus.CREATED);

	}
}
