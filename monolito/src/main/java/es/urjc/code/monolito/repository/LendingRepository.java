package es.urjc.code.monolito.repository;

import es.urjc.code.monolito.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {



}
