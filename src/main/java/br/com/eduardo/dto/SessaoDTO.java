package br.com.eduardo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessaoDTO {
	
	private Long id;
	private String descricao;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime inicio;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime fim;
	private Long idPauta;
	
	
}
