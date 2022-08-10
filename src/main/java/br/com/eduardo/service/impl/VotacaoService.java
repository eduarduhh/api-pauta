package br.com.eduardo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.dto.ResultadoVotoDTO;
import br.com.eduardo.dto.VotoDTO;
import br.com.eduardo.entity.Associado;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.entity.Votacao;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.integration.CpfService;
import br.com.eduardo.repository.VotacaoRepository;
import br.com.eduardo.service.IGenericService;

@Service
public class VotacaoService implements IGenericService<Votacao> {

	private static final String VOTACAO_NAO_ENCONTRADA = "Votação não encontrada";

	@Autowired
	private VotacaoRepository votacaoRepository;

	@Autowired
	private SessaoService sessaoService;

	@Autowired
	private AssociadoService associadoService;
	
	@Autowired
	private CpfService cpfService;

	@Override
	public List<Votacao> findAll() {
		return votacaoRepository.findAll();
	}

	@Override
	public Votacao findById(Long id) {
		return votacaoRepository.findById(id).orElseThrow(() -> new NotFoundException(VOTACAO_NAO_ENCONTRADA));
	}

	public Votacao findByAssociadoIdAndSessaoId(Long associadoId, Long sessaoId) {
		return votacaoRepository.findByAssociadoIdAndSessaoId(associadoId, sessaoId)
				.orElseThrow(() -> new NotFoundException(VOTACAO_NAO_ENCONTRADA));
	}

	@Override
	public Votacao save(Votacao t) {
		return votacaoRepository.save(t);
	}

	@Override
	public void delete(Votacao t) {
		votacaoRepository.delete(t);
	}

	@Override
	public long count() {
		return votacaoRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		votacaoRepository.deleteById(id);

	}

	public ResultadoVotoDTO resultadoVotacao(Long idSessao) {
		
		Sessao sessao = sessaoService.findSessaoComVotos(idSessao);
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(sessao.getFim().isAfter(dateTime)) {
			throw new BusinessException("Votação em aberto");
		}
		ResultadoVotoDTO dto = new ResultadoVotoDTO();
		
		Long votosSIm = sessao.getVotacaos().stream().filter(e -> e.getVoto()).count();
		Long votosNao = sessao.getVotacaos().stream().filter(e -> !e.getVoto()).count();
		dto.setIdPauta(sessao.getPauta().getId());
		dto.setDescricaoPauta(sessao.getPauta().getDescricao());
		dto.setIdSessao(sessao.getId());
		dto.setDescricaoSessao(sessao.getDescricao());
		dto.setInicio(sessao.getInicio());
		dto.setFim(sessao.getFim());
		dto.setVotoSim(votosSIm);
		dto.setVotoNao(votosNao);
		return dto;
	}

	public void votar(VotoDTO dto) {

		if (!dto.getVoto().equals("SIM") && !dto.getVoto().equals("NÃO")) {
			throw new BusinessException("voto somente SIM ou NÃO" +  (!dto.getVoto().equals("SIM") || !dto.getVoto().equals("NÃO")));
		}

		Sessao sessao = sessaoService.findByIdAndIndicExclusao(dto.getIdSessao(), false);
		Associado associado = associadoService.findById(dto.getIdAssociado());
		
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		cpfService.isAbleToVote(associado.getCpf());
		
		if (sessao.getFim().isAfter(dateTime)) {
			throw new BusinessException("Votação já foi encerrada");	
		}
		
		
		Optional<Votacao> votado = votacaoRepository.findByAssociadoIdAndSessaoId(dto.getIdAssociado(),dto.getIdSessao());
		
		if(votado.isPresent()) {
			throw new BusinessException("O associado ja voltou");
		}
		
		Votacao votacao = new Votacao(sessao, associado, dto.getVoto());
		votacaoRepository.save(votacao);
	}

}
