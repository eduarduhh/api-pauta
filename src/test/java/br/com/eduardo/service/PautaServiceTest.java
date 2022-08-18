package br.com.eduardo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.exception.NotFoundException;
import br.com.eduardo.repository.PautaRepository;
import br.com.eduardo.service.impl.PautaService;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

	@InjectMocks
	private PautaService pautaService;

	@Mock
	private PautaRepository pautaRepository;

	@Test
	public void deveEncontrarPauta() {

		long codigo = new Random().nextLong();

		Pauta pauta = Pauta.builder().id(codigo).build();

		when(pautaRepository.findById(any(Long.class))).thenReturn(Optional.of(pauta));

		Pauta p = pautaService.findById(codigo);

		verify(pautaRepository).findById(codigo);

		assert (p.getId() == codigo);
	}
	
	@Test
	public void deveOcorrerErroQuandoProcuraPauta() {

		long codigo = new Random().nextLong();
		
		NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
			 pautaService.findById(codigo);
		});
		
		
		verify(pautaRepository).findById(codigo);
		assertTrue(exception.getMessage().contains(PautaService.PAUTA_NAO_ENCONTRADA));
	}


}
