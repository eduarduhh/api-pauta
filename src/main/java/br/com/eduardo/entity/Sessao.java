package br.com.eduardo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Sessao implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private Boolean indicExclusao;
	

	@ManyToOne
	private Pauta pauta;
	
	@OneToMany(mappedBy ="sessao")
	@JsonIgnoreProperties(value = {"sessao"}, allowSetters = true)
	private List<Votacao> votacaos;
	
	
	public Sessao(String descricao, Pauta pauta) {
		this.descricao = descricao;
		this.pauta = pauta;
		this.indicExclusao = false;
		LocalDateTime  data = LocalDateTime.now();
	    this.inicio = data;
	    this.fim = data.minusDays(1);
	   
	}

}
