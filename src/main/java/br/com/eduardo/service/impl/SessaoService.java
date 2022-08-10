package br.com.eduardo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.SessaoRepository;
import br.com.eduardo.service.IGenericService;

@Service
public class SessaoService implements IGenericService<Sessao> {
	
	private static final String SESSAO_NAO_ENCONTRADA =  "Sessão não encontrada";
	
	private static final String VOTACAO_NAO_ENCONTRADA = "Votação não encontrada";


	@Autowired
	private SessaoRepository sessaoRepository;

	@Override
	public List<Sessao> findAll() {
		return sessaoRepository.findAll();
	}
	

	public List<SessaoDTO> findAllSessaoDTO() {
		List<SessaoDTO> sessaoDTOs = new ArrayList<>();
		List<Sessao> sessaos = sessaoRepository.findAllByIndicExclusao(false);
		
		for(Sessao s : sessaos) {
			sessaoDTOs.add(new SessaoDTO(s));
		}

		return sessaoDTOs;
	}
	
	@Override
	public Sessao save(Sessao t) {
		return sessaoRepository.save(t);
	}

	@Override
	public Sessao findById(Long id) {
		return sessaoRepository.findById(id).orElseThrow(() -> new NotFoundException(SESSAO_NAO_ENCONTRADA));
	}
	
	public Sessao findSessaoComVotos(Long id) {
		return sessaoRepository.findSessaoComVotos(id).orElseThrow(() -> new NotFoundException(VOTACAO_NAO_ENCONTRADA));
	}
	
	
	public Sessao findByIdAndIndicExclusao(Long id, Boolean indicExclusao) {
		return sessaoRepository.findByIdAndIndicExclusao(id,indicExclusao).orElseThrow(() -> new NotFoundException(SESSAO_NAO_ENCONTRADA));
	}
	
	
	public SessaoDTO findByIdAndIndicExclusaoDTO(Long id, Boolean indicExclusao) {
		 return new SessaoDTO(this.findByIdAndIndicExclusao(id, indicExclusao));
	}
	
	public Optional<Sessao> findByDescricao(String descricao) {
		return sessaoRepository.findByDescricao(descricao);
	}
	
	@Override
	public void delete(Sessao t) {
		sessaoRepository.delete(t);		
	}

	@Override
	public void deleteById(Long id) {
		sessaoRepository.deleteById(id);
		
	}
	
	@Override
	public long count() {
		return sessaoRepository.count();
	}
	
	
	public List<Sessao> findAllByFimBetween(LocalDateTime inicio, LocalDateTime fim) {
		return sessaoRepository.findAllByFimBetween(inicio, fim);
	}


	
}
