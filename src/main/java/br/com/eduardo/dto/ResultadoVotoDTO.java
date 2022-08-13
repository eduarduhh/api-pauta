package br.com.eduardo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultadoVotoDTO {
	
	private Long idPauta;
	private String descricaoPauta;
	private Long idSessao;
	private String descricaoSessao;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private Long votoSim;
	private Long votoNao;

}
