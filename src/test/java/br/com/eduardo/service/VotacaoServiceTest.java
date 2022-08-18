package br.com.eduardo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eduardo.clients.CPFClient;
import br.com.eduardo.clients.CpfResponse;
import br.com.eduardo.dto.VotoDTO;
import br.com.eduardo.entity.Pauta;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.entity.Votacao;
import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.repository.SessaoRepository;
import br.com.eduardo.repository.VotacaoRepository;
import br.com.eduardo.service.impl.VotacaoService;

@ExtendWith(MockitoExtension.class)
public class VotacaoServiceTest {

	@InjectMocks
	private VotacaoService votacaoService;

	@Mock
	private SessaoRepository sessaoRepository;

	@Mock
	private VotacaoRepository votacaoRepository;

	@Mock
	private CPFClient cpfClient;

	private static VotoDTO retornaVotoDTO() {
		return VotoDTO.builder().cpf("11111111").idSessao(1L).voto("SIM").build();

	}

	private static Pauta retornaPauta() {
		return Pauta.builder().id(1L).descricao("Pauta01").build();
	}

	private static Sessao retornaSessao() {
		LocalDateTime data = LocalDateTime.now();
		return Sessao.builder().descricao("Sessao 1").pauta(retornaPauta()).indicExclusao(false).inicio(data)
				.fim(data.minusDays(1)).build();

	}
	private static Votacao retornaVotacao() {
		return Votacao.builder().cpf("111111").id(1L).sessao(retornaSessao()).voto(true).build();
	}

	@Test
	public void deveRetornarErroQuandovotoForDiferenteDeSimeNao() {

		VotoDTO votoDTO = retornaVotoDTO();
		votoDTO.setVoto("A FAVOR");

		BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
			votacaoService.votar(votoDTO);
		});
		assertTrue(exception.getMessage().contains("voto somente SIM ou NÃO"));

	}

	@Test
	public void deveRetornarErroSessaoNaoExistir() {

		VotoDTO votoDTO = retornaVotoDTO();
		votoDTO.setIdSessao(4l);

		BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
			votacaoService.votar(votoDTO);
		});
		assertTrue(exception.getMessage().contains("Sessão não encontrada"));

	}

	@Test
	public void deveRetornarVotaçãoEncerrada() {
		LocalDateTime data = LocalDateTime.now();

		VotoDTO votoDTO = retornaVotoDTO();

		Sessao sessao = retornaSessao();
		sessao.setInicio(data);
		sessao.setFim(data.minusDays(-1));

		when(sessaoRepository.findById(any(Long.class))).thenReturn(Optional.of(sessao));

		BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
			votacaoService.votar(votoDTO);
		});

		assertTrue(exception.getMessage().contains("Votação já foi encerrada"));

		verify(votacaoRepository, never()).save(any(Votacao.class));

	}

	@Test
	public void devereRetornarErroNoCPF() {
		
		VotoDTO votoDTO = retornaVotoDTO();

		Sessao sessao = retornaSessao();

		CpfResponse cpfResponse = CpfResponse.builder().status("UNABLE_TO_VOTE").build();

		when(sessaoRepository.findById(any(Long.class))).thenReturn(Optional.of(sessao));

		when(cpfClient.getCpf(any(String.class))).thenReturn(cpfResponse);

		BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
			votacaoService.votar(votoDTO);
		});

		assertTrue(exception.getMessage().contains("CPF Invalido"));

		verify(votacaoRepository, never()).save(any(Votacao.class));

	}

	@Test
	public void deveVerificarSeOUsuarioJaVotou() {
		VotoDTO votoDTO = retornaVotoDTO();

		Sessao sessao = retornaSessao();
		
		CpfResponse cpfResponse = CpfResponse.builder().status("ABLE_TO_VOTE").build();
		
		Votacao votacao = retornaVotacao();
		

		when(sessaoRepository.findById(any(Long.class))).thenReturn(Optional.of(sessao));

		when(cpfClient.getCpf(any(String.class))).thenReturn(cpfResponse);
		
		when(votacaoRepository.findByCpfAndSessaoId(any(String.class), any(Long.class))).thenReturn(Optional.of(votacao));

		BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
			votacaoService.votar(votoDTO);
		});

		assertTrue(exception.getMessage().contains("O associado ja voltou"));

		verify(votacaoRepository, never()).save(any(Votacao.class));

	}
	
	
	@Test
	public void deveVotarCorretamente() {
		VotoDTO votoDTO = retornaVotoDTO();

		Sessao sessao = retornaSessao();
		
		CpfResponse cpfResponse = CpfResponse.builder().status("ABLE_TO_VOTE").build();
		
	
		when(sessaoRepository.findById(any(Long.class))).thenReturn(Optional.of(sessao));

		when(cpfClient.getCpf(any(String.class))).thenReturn(cpfResponse);
		
		when(votacaoRepository.findByCpfAndSessaoId(any(String.class), any(Long.class))).thenReturn(Optional.empty());

		votacaoService.votar(votoDTO);
		
	}

}
