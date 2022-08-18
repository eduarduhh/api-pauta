package br.com.eduardo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VotoDTO {
	
	private Long idSessao;
	private String cpf;
	private String voto;
	
}
