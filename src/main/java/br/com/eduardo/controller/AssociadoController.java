package br.com.eduardo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.entity.Associado;
import br.com.eduardo.service.impl.AssociadoService;

@RestController
@RequestMapping("/v1/associado")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Associado associado) {

		associadoService.cadastrado(associado.getCpf());	
		associado.setId(null);
	
		Associado save = associadoService.save(associado);

		return new ResponseEntity<Associado>(save, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Associado associado) {
		
		associadoService.findById(associado.getId());
		Associado update = associadoService.save(associado);
		return new ResponseEntity<Associado>(update, HttpStatus.OK);
	}

	
}
