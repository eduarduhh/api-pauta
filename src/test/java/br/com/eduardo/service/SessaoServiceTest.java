package br.com.eduardo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.eduardo.entity.Pauta;
import br.com.eduardo.service.impl.PautaService;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

	@Mock
	private PautaService pautaService;
		
	@Test
	public void findAll() {
		assertEquals(0, pautaService.findAll().size());
	}
	
	@Test
	public void findAllByIndicExclusao() {
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setDescricao("Pauta");
		pauta.setIndicExclusao(false);
		
        Mockito.when(pautaService.findAllByIndicExclusao(false)).thenReturn(Collections.singletonList(pauta));
        List<Pauta> findAllByIndicExclusao = pautaService.findAllByIndicExclusao(false);
		
		assertEquals(1, findAllByIndicExclusao.size());
	}
    
	
	
	@Test
	public void  findByIdoAndIndicExclusao() {
		
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setDescricao("Pauta");
		pauta.setIndicExclusao(false);
		
		 Mockito.when(pautaService.findByIdoAndIndicExclusao(1L, false)).thenReturn(pauta);
		 Pauta findByIdoAndIndicExclusao = pautaService.findByIdoAndIndicExclusao(1L, false);
		 
		 assertEquals(1L, findByIdoAndIndicExclusao.getId());
		 
		 Mockito.when(pautaService.findByIdoAndIndicExclusao(1L, false)).thenReturn(null);
		 
		 Pauta findByIdoAndIndicExclusao1 = pautaService.findByIdoAndIndicExclusao(1L, false);

		 
		 assertNull(findByIdoAndIndicExclusao1);

	}
	
	@Test
	public void findByDescricao() {
		
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setDescricao("Pauta");
		pauta.setIndicExclusao(false);
		
		 Mockito.when(pautaService.findByDescricao("Pauta" )).thenReturn(pauta);
		 Pauta findByDescricao = pautaService.findByDescricao("Pauta");
		 assertEquals("Pauta", findByDescricao.getDescricao());
		 
		 	
	}
	
	@Test
	public void findByDescricaoAndIndicExclusao(){
		
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setDescricao("Pauta");
		pauta.setIndicExclusao(false);
		
		 Mockito.when(pautaService.findByDescricaoAndIndicExclusao("Pauta", false)).thenReturn(pauta);
		 
		 Pauta findByDescricaoAndIndicExclusao = pautaService.findByDescricaoAndIndicExclusao("Pauta", false);
		 
		 assertEquals("Pauta", findByDescricaoAndIndicExclusao.getDescricao());
		 
	}

	

}
