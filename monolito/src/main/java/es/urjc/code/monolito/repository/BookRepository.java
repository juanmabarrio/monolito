package es.urjc.code.monolito.repository;

import es.urjc.code.monolito.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
