package br.com.eduardo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.entity.Pauta;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.PautaRepository;
import br.com.eduardo.repository.SessaoRepository;

@Service
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	public List<SessaoDTO> findAllSessaoDTO() {
		List<SessaoDTO> sessaoDTOs = new ArrayList<>();
		List<Sessao> sessaos = sessaoRepository.findAllByIndicExclusao(false);

		for (Sessao s : sessaos) {
			sessaoDTOs.add(new SessaoDTO(s));
		}

		return sessaoDTOs;
	}

	public SessaoDTO salvarSessao(SessaoDTO sessaoDTO) {
		sessaoDTO.setId(null);
		Optional<Pauta> pauta = pautaRepository.findById(sessaoDTO.getIdPauta());

		if (!pauta.isPresent()) {
			throw new NotFoundException("Pauta não encontrada");
		}

		Optional<Sessao> oSessao = sessaoRepository.findByDescricao(sessaoDTO.getDescricao());

		if (!oSessao.isPresent()) {
			Sessao sessao = new Sessao(sessaoDTO.getDescricao(), pauta.get());
			Sessao sessaoRetorno = sessaoRepository.save(sessao);
			SessaoDTO dto = new SessaoDTO(sessaoRetorno);
			return dto;
		}

		throw new BusinessException("Sessão já encontrada");
	}

}
