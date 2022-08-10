package br.com.eduardo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotoDTO {
	
	private Long idAssociado;
	private Long idSessao;
	private String voto;
	
}
