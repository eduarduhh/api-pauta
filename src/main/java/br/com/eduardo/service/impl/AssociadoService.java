package br.com.eduardo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.entity.Associado;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.AssociadoRepository;
import br.com.eduardo.service.IGenericService;

@Service
public class AssociadoService implements IGenericService<Associado> {

	private static final String ASSOCIADO_NAO_ENCONTRADA ="Associado não encontrado";
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Override
	public List<Associado> findAll() {
		return associadoRepository.findAll();
	}
	

	@Override
	public Associado save(Associado t) {
		return associadoRepository.save(t);
	}

	@Override
	public Associado findById(Long id) {
		return associadoRepository.findById(id).orElseThrow(() -> new NotFoundException(ASSOCIADO_NAO_ENCONTRADA));
	}
	
	@Override
	public void delete(Associado t) {
		associadoRepository.delete(t);		
	}

	@Override
	public void deleteById(Long id) {
		associadoRepository.deleteById(id);
		
	}
	
	@Override
	public long count() {
		return associadoRepository.count();
	}


	public void cadastrado(String cpf) {
		Optional<Associado> findByCpf = associadoRepository.findByCpf(cpf);
		if(findByCpf.isPresent()) {
			throw new BusinessException("Associado já cadastrado");
		}
		
	}
	
}
