package br.com.eduardo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.eduardo.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	Optional<Sessao> findByDescricao(String descricao);

	 @Query(value = "SELECT s FROM Sessao s  JOIN FETCH s.votacaos WHERE s.id = :id")
	 Optional<Sessao> findSessaoComVotos(Long id);
	  
	 List<Sessao> findAllByFimBetween(LocalDateTime inicio, LocalDateTime fim);
}
