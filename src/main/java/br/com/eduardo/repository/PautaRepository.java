package br.com.eduardo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	Optional<Pauta> findByDescricao(String descricao);
	
}
