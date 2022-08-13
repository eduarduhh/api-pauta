package br.com.eduardo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.PautaRepository;

@Service
public class PautaService  {
	
	private static final String PAUTA_NAO_ENCONTRADA = "Pauta Não encontrada";
	private static final String PAUTA_JA_CADASTRADA = "Pauta ja cadastrada";


	@Autowired
	private PautaRepository pautaRepository;

	
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}
	
	public Pauta findById(Long id) {
		return pautaRepository.findById(id). orElseThrow(() -> new NotFoundException(PAUTA_NAO_ENCONTRADA));
	}
	

	public Pauta save(Pauta pauta) {
		
		Optional<Pauta> findByDescricao = pautaRepository.findByDescricao(pauta.getDescricao());
		pauta.setId(null);
		if(findByDescricao.isPresent()) {
			throw new BusinessException(PAUTA_JA_CADASTRADA);
		}
	
		return pautaRepository.save(pauta);
	}
	
}
