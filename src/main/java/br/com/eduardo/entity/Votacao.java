package br.com.eduardo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Votacao implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Sessao sessao;
	
	@ManyToOne
	private Associado associado;
	
	private Boolean voto;
	
	
	
	public Votacao(Sessao sessao, Associado associado, String voto) {
		
		this.sessao = sessao;
		this.associado = associado;
		this.voto = voto.equals("SIM") ? true : false;
		
	}
	
}
