package br.com.eduardo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eduardo.dto.SessaoDTO;
import br.com.eduardo.service.impl.SessaoService;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

	@Mock
	private SessaoService pautaService;
		
	@Test
	public void findAll() {
		assertEquals(0, pautaService.findAll().size());
	}
	
	@Test
	public void findAllByIndicExclusao() {
		SessaoDTO dto = new SessaoDTO();
		dto.setId(1L);
		dto.setDescricao("Sessao");
		
        Mockito.when(pautaService.findAllSessaoDTO()).thenReturn(Collections.singletonList(dto));
       List<SessaoDTO> findAllSessaoDTO = pautaService.findAllSessaoDTO();
		
		assertEquals(1, findAllSessaoDTO.size());
	}
    

}
