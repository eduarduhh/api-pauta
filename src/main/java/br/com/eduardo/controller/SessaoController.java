package br.com.eduardo.controller;

import java.util.Optional;

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

import br.com.eduardo.dto.ResponseDTO;
import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.entity.Pauta;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.service.impl.PautaService;
import br.com.eduardo.service.impl.SessaoService;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

	@Autowired
	private SessaoService sessaoService;
	@Autowired
	private PautaService pautaService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(sessaoService.findAllSessaoDTO(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable(value = "id") long id) {
		SessaoDTO dto = sessaoService.findByIdAndIndicExclusaoDTO(id, false);
		return new ResponseEntity<SessaoDTO>(dto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody SessaoDTO sessaoDTO) {
		sessaoDTO.setId(null);
		Pauta pauta = pautaService.findById(sessaoDTO.getIdPauta());

		Optional<Sessao> oSessao = sessaoService.findByDescricao(sessaoDTO.getDescricao());

		if (!oSessao.isPresent()) {
			Sessao sessao = new Sessao(sessaoDTO.getDescricao(), pauta);
			Sessao sessaoRetorno = sessaoService.save(sessao);
			SessaoDTO dto = new SessaoDTO(sessaoRetorno);
			return new ResponseEntity<SessaoDTO>(dto, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<ResponseDTO>(new ResponseDTO("Sessao já cadastrada"), HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody SessaoDTO sessaoDTO) {
		Pauta pauta = pautaService.findByIdoAndIndicExclusao(sessaoDTO.getIdPauta(), false);
		Sessao sessao = sessaoService.findByIdAndIndicExclusao(sessaoDTO.getId(), false);
		
		sessao.setPauta(pauta);
		sessao.setDescricao(sessaoDTO.getDescricao());
		Sessao sessaoRetorno = sessaoService.save(sessao);

		return new ResponseEntity<SessaoDTO>(new SessaoDTO(sessaoRetorno), HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
		Sessao sessao = sessaoService.findByIdAndIndicExclusao(id, false);
		sessao.setIndicExclusao(true);
		sessaoService.save(sessao);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
