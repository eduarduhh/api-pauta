package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.service.impl.PautaService;

@RestController
@RequestMapping("/v1/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(pautaService.findAllByIndicExclusao(false), HttpStatus.OK);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable(value = "id") long id) {
		Pauta p = pautaService.findByIdoAndIndicExclusao(id, false);
		return new ResponseEntity<Pauta>(p	, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Pauta pauta) {

		pautaService.cadaastrado(pauta.getDescricao());
	
		pauta.setId(null);
		pauta.setIndicExclusao(false);
		Pauta save = pautaService.save(pauta);

		return new ResponseEntity<Pauta>(save, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Pauta pauta) {
		Pauta p = pautaService.findByIdoAndIndicExclusao(pauta.getId(), false);
		p.setDescricao(pauta.getDescricao());
		pautaService.save(p);
		return new ResponseEntity<Pauta>(p, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
		Pauta pauta = pautaService.findById(id);
		pauta.setIndicExclusao(true);
		pautaService.save(pauta);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
