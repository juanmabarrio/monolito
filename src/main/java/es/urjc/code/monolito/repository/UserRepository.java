package es.urjc.code.monolito.repository;

import es.urjc.code.monolito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



}
