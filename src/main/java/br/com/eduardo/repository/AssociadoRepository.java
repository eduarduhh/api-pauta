package br.com.eduardo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.entity.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	Optional<Associado> findByMatricula(String matricula);

	Optional<Associado> findByCpf(String cpf);
}
