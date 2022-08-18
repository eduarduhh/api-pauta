package br.com.eduardo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.entity.Pauta;
import br.com.eduardo.entity.Sessao;
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
		
		List<Sessao> sessaos = sessaoRepository.findAll();

		for (Sessao s : sessaos) {
			SessaoDTO dto = SessaoDTO.builder().
					        id(s.getId())
					        .descricao(s.getDescricao())
					        .idPauta(s.getPauta().getId())
					         .inicio(s.getInicio()).fim(s.getFim()).build();

			sessaoDTOs.add(dto);
		}

		return sessaoDTOs;
	}

	public SessaoDTO salvarSessao(SessaoDTO sessaoDTO) {
		sessaoDTO.setId(null);
		Optional<Pauta> pauta = pautaRepository.findById(sessaoDTO.getIdPauta());

		if (!pauta.isPresent()) {
			throw new NotFoundException("Pauta não encontrada");
		}

		LocalDateTime data = LocalDateTime.now();

		Sessao sessao = Sessao.builder().descricao(sessaoDTO.getDescricao()).pauta(pauta.get()).indicExclusao(false)
				.inicio(data).fim(data.minusDays(1)).build();

		Sessao sessaoRetorno = sessaoRepository.save(sessao);

		SessaoDTO dto = SessaoDTO.builder().id(sessaoRetorno.getId()).descricao(sessaoRetorno.getDescricao())
				.idPauta(sessaoRetorno.getPauta().getId()).inicio(sessaoRetorno.getInicio()).fim(sessaoRetorno.getFim())
				.build();
		return dto;

	}

}
