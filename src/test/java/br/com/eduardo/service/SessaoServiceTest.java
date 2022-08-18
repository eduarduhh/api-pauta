package br.com.eduardo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.entity.Pauta;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.PautaRepository;
import br.com.eduardo.repository.SessaoRepository;
import br.com.eduardo.service.impl.SessaoService;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

	@InjectMocks
	private SessaoService sessaoService;

	@Mock
	private PautaRepository pautaRepository;

	@Mock
	private SessaoRepository sessaoRepository;

	@Test
	public void deveSalvarSessao() {
		LocalDateTime data = LocalDateTime.now();
		long codigo = new Random().nextLong();

		SessaoDTO sessaoDTO = SessaoDTO.builder().id(codigo).descricao("Descricao Sessao").idPauta(1L).build();

		Pauta pauta = Pauta.builder().id(codigo).descricao("Pauta01").build();

		Sessao sessao = Sessao.builder().descricao(sessaoDTO.getDescricao()).pauta(pauta).indicExclusao(false)
				.inicio(data).fim(data.minusDays(1)).build();

		when(pautaRepository.findById(any(Long.class))).thenReturn(Optional.of(pauta));

		when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

		sessaoService.salvarSessao(sessaoDTO);

	}

	@Test
	public void deveRetornarUmErroSalvarSessao() {

		long codigo = new Random().nextLong();

		SessaoDTO sessaoDTO = SessaoDTO.builder().id(codigo).descricao("Descricao Sessao").idPauta(1L).build();

		NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
			sessaoService.salvarSessao(sessaoDTO);
		});

		verify(sessaoRepository, never()).save(any(Sessao.class));
		assertTrue(exception.getMessage().contains("Pauta não encontrada"));

	}
	
	
	
	
	@Test
	public void listarSessao() {
		LocalDateTime data = LocalDateTime.now();
		
		Pauta pauta = Pauta.builder().id(1L).descricao("Pauta01").build();

		Sessao sessao = Sessao.builder().descricao("Sessao 1").pauta(pauta).indicExclusao(false)
				.inicio(data).fim(data.minusDays(1)).build();
		

		when(sessaoRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(sessao)));
		
		
		assertEquals(1, sessaoService.findAllSessaoDTO().size());

	}

}
