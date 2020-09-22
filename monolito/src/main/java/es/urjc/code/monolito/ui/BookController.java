package es.urjc.code.monolito.ui;

import es.urjc.code.monolito.model.Book;
import es.urjc.code.monolito.model.Lending;
import es.urjc.code.monolito.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class BookController {


	@Autowired
	private BookRepository bookRepo;
	
	@PostConstruct
	public void init() {

	}
	
	@GetMapping("/book")
	public List<Book> allBooks() {
		return bookRepo.findAll();
	}
	
	@GetMapping("/book/{id}")
	public Book book(@PathVariable long id) {
		return bookRepo.findById(id).get();
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable long id) {
		Book book = bookRepo.findById(id).orElse(null);
		if (book == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(book,HttpStatus.OK);
	}


	@PostMapping("/book")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Book newBook(@RequestBody Book book) {
		Book bookWithId = bookRepo.save(book);
		return bookWithId;
	}
	

}
