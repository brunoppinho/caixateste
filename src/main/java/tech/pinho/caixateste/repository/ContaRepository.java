package tech.pinho.caixateste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.pinho.caixateste.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
