package br.com.eduardo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.clients.CPFClient;
import br.com.eduardo.clients.CpfResponse;
import br.com.eduardo.dto.ResultadoVotoDTO;
import br.com.eduardo.dto.VotoDTO;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.entity.Votacao;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.repository.SessaoRepository;
import br.com.eduardo.repository.VotacaoRepository;

@Service
public class VotacaoService  {


	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Autowired
	private SessaoRepository  sessaoRepository;
	
	@Autowired
	private CPFClient cpfClient;

	
	public ResultadoVotoDTO resultadoVotacao(Long idSessao) {
		
		Optional<Sessao> sesssionById = sessaoRepository.findById(idSessao);
		
		if(!sesssionById.isPresent()) {
		  throw new BusinessException("Votação já foi encerrada");	
		}
		
		Sessao sessao = sesssionById.get();
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(sessao.getFim().isAfter(dateTime)) {
			throw new BusinessException("Votação em aberto");
		}
	
		Long votosSIm = sessao.getVotacaos().stream().filter(e -> e.getVoto()).count();
		Long votosNao = sessao.getVotacaos().stream().filter(e -> !e.getVoto()).count();
		
		ResultadoVotoDTO dto = ResultadoVotoDTO.builder().idPauta(sessao.getPauta().getId())
														 .descricaoPauta(sessao.getPauta().getDescricao())
														 .idSessao(sessao.getId())
														 .descricaoSessao(sessao.getDescricao())
														 .inicio(sessao.getInicio())
														 .fim(sessao.getFim())
														 .votoSim(votosSIm)
														 .votoNao(votosNao)
														 .build();

		return dto;
	}

	public void votar(VotoDTO dto) {

		if (!dto.getVoto().equals("SIM") && !dto.getVoto().equals("NÃO")) {
			throw new BusinessException("voto somente SIM ou NÃO");
		}

		Optional<Sessao> sesssionById = sessaoRepository.findById(dto.getIdSessao());
		
		if(!sesssionById.isPresent()) {
		  throw new BusinessException("Sessão não encontrada");	
		}
		
		
		LocalDateTime dateTime = LocalDateTime.now();
		CpfResponse cpfResponse = cpfClient.getCpf(dto.getCpf());
		
		Sessao sessao = sesssionById.get();
		
		if (sessao.getFim().isAfter(dateTime)) {
			throw new BusinessException("Votação já foi encerrada");	
		}
		
		if(!cpfResponse.getStatus().equals("ABLE_TO_VOTE")) {
			throw new BusinessException("CPF Invalido");
		}
		
		Optional<Votacao> votado = votacaoRepository.findByCpfAndSessaoId(dto.getCpf(),dto.getIdSessao());
		
		if(votado.isPresent()) {
			throw new BusinessException("O associado ja voltou");
		}
		
		boolean voto = dto.getVoto().equals("SIM") ? true : false;
		
		Votacao votacao = Votacao.builder()
								 .cpf(dto.getCpf())
								 .sessao(sessao)
								 .voto(voto)
								 .build();
		
		votacaoRepository.save(votacao);
	}

}
