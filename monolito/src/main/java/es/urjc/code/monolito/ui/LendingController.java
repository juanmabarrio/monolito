package es.urjc.code.monolito.ui;


import es.urjc.code.monolito.model.Book;
import es.urjc.code.monolito.model.Lending;
import es.urjc.code.monolito.model.User;
import es.urjc.code.monolito.repository.LendingRepository;
import es.urjc.code.monolito.repository.BookRepository;
import es.urjc.code.monolito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
public class LendingController {


	@Autowired
	private LendingRepository lendingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	@PostConstruct
	public void init() {
	}
	
	@GetMapping("/lending")
	public List<Lending> allLendings() {
		return lendingRepository.findAll();
	}
	
	@GetMapping("/lending/{id}")
	public Lending lending(@PathVariable long id) {
		return lendingRepository.findById(id).get();
	}

	@DeleteMapping("/lending/{id}")
	public ResponseEntity<Lending> deleteLending(@PathVariable long id) {
		Lending lending = lendingRepository.findById(id).orElse(null);
		if (lending == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(lending,HttpStatus.OK);
	}

	@PostMapping("/lending")
	public ResponseEntity<Lending> newLending(@RequestParam Long userId, @RequestParam Long bookId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		};
		if (user.getMaxLending()==0){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Book book = bookRepository.findById(bookId).orElse(null);
		if (book==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		};
		if (book.getStock()==0){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		book.setStock(book.getStock()-1);
		user.setMaxLending(user.getMaxLending()-1);
		Lending lending = new Lending(book,user);
		Lending lendingWithId = lendingRepository.save(lending);
		bookRepository.save(book);
		userRepository.save(user);
		return new ResponseEntity<>(lendingWithId,HttpStatus.CREATED);
	}



}
