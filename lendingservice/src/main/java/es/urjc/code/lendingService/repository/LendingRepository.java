package es.urjc.code.lendingService.repository;

import es.urjc.code.lendingService.model.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {



}
