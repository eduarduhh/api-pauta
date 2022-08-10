package br.com.eduardo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.eduardo.entity.Sessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessaoDTO {
	
	private Long id;
	private String descricao;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime inicio;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime fim;
	private Long idPauta;
	
	
	public SessaoDTO(Sessao sessao) {
		this.id = sessao.getId();
		this.descricao = sessao.getDescricao();
		this.idPauta = sessao.getPauta().getId();
		this.inicio = sessao.getInicio();
		this.fim  = sessao.getFim();
	}
}
