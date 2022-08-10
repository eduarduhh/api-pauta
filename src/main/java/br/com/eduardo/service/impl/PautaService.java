package br.com.eduardo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.PautaRepository;
import br.com.eduardo.service.IGenericService;

@Service
public class PautaService implements IGenericService<Pauta> {
	
	private static final String PAUTA_NAO_ENCONTRADA = "Pauta Não encontrada";
	private static final String PAUTA_JA_CADASTRADA = "Pauta ja cadastrada";


	@Autowired
	private PautaRepository pautaRepository;

	@Override
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}
	
	public List<Pauta> findAllByIndicExclusao(Boolean excluido) {
		return pautaRepository.findAllByIndicExclusao(excluido);
	}

	
	@Override
	public Pauta save(Pauta t) {
		return pautaRepository.save(t);
	}

	@Override
	public Pauta findById(Long id) {
		return pautaRepository.findById(id). orElseThrow(() -> new NotFoundException(PAUTA_NAO_ENCONTRADA));
	}
	
	
	public Pauta findByIdoAndIndicExclusao(Long id, Boolean indicExclusao) {
		return  pautaRepository.findByIdAndIndicExclusao(id, indicExclusao).
				 orElseThrow(() -> new NotFoundException(PAUTA_NAO_ENCONTRADA));
		
	}
	

	public Pauta findByDescricao(String descricao) {
		return pautaRepository.findByDescricao(descricao). orElseThrow(() -> new NotFoundException(PAUTA_NAO_ENCONTRADA));
	}
	
	public Pauta findByDescricaoAndIndicExclusao(String descricao, Boolean indicExclusao){
		return pautaRepository.findByDescricaoAndIndicExclusao(descricao, indicExclusao).
				 orElseThrow(() -> new NotFoundException(PAUTA_JA_CADASTRADA));
	}
	
	public void cadaastrado(String descricao){
		 Optional<Pauta> cadaastrado = pautaRepository.findByDescricaoAndIndicExclusao(descricao, false);
		 if(cadaastrado.isPresent()) {
			 throw new NotFoundException(PAUTA_NAO_ENCONTRADA);
		 }
		 
	}

	@Override
	public void delete(Pauta t) {
		pautaRepository.delete(t);		
	}

	@Override
	public void deleteById(Long id) {
		pautaRepository.deleteById(id);
		
	}
	
	@Override
	public long count() {
		return pautaRepository.count();
	}

	
}
