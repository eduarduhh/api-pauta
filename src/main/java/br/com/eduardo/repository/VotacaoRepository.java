package br.com.eduardo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.entity.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	
	
	Optional<Votacao> findByAssociadoIdAndSessaoId(Long associadoId, Long sessaoId);
	
	Optional<Votacao> findBySessaoId(Long sessaoId);
	


}
