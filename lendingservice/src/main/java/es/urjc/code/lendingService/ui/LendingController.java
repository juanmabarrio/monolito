package es.urjc.code.lendingService.ui;


import es.urjc.code.lendingService.consuming.BookDTO;
import es.urjc.code.lendingService.consuming.UserDTO;
import es.urjc.code.lendingService.repository.LendingRepository;
import es.urjc.code.lendingService.model.Lending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class LendingController {


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@Autowired
	private LendingRepository lendingRepository;

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
		RestTemplate restTemplate = new RestTemplate();
		Lending lending = lendingRepository.findById(id).orElse(null);
		if (lending == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		//increase stock and credit
		//get user
		ResponseEntity<UserDTO> userUpdateResponseEntity = restTemplate.getForEntity("http://localhost:8080/user/"+lending.getUserId(), UserDTO.class);
		UserDTO user = userUpdateResponseEntity.getBody();
		//we update the user with maxLending+1
		compensateMaxLending(user);

		//get book
		ResponseEntity<BookDTO> bookUpdateResponseEntity = restTemplate.getForEntity("http://localhost:8080/book/"+lending.getBookId(), BookDTO.class);
		BookDTO book = bookUpdateResponseEntity.getBody();
		//we update the book with stock+1
		compensateStock(book);

		return new ResponseEntity<>(lending,HttpStatus.OK);
	}

	private void compensateStock(BookDTO book) {
		RestTemplate restTemplate = new RestTemplate();
		book.setStock(book.getStock()+1);
		;
		HttpEntity<BookDTO> bookEntity = new HttpEntity<BookDTO>(book);
		restTemplate.exchange("http://localhost:8080/user/", HttpMethod.PUT, bookEntity, BookDTO.class);
	}

	@PostMapping("/lending")
	public ResponseEntity<Lending> newLending(@RequestParam Long userId, @RequestParam Long bookId) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<UserDTO> userUpdateResponseEntity = restTemplate.getForEntity("http://localhost:8080/user/"+userId, UserDTO.class);
		if (userUpdateResponseEntity.getStatusCodeValue()!=200)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		if (userUpdateResponseEntity.getBody().getMaxLending()==0)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		UserDTO user = userUpdateResponseEntity.getBody();
		user.setMaxLending(user.getMaxLending()-1);

		HttpEntity<UserDTO> userEntity = new HttpEntity<UserDTO>(user);
		//we update the user with maxLending-1
		restTemplate.exchange("http://localhost:8080/user/", HttpMethod.PUT, userEntity, UserDTO.class);

		//we look for the book now, to check availabilty...
		ResponseEntity<BookDTO> bookResponseEntity = restTemplate.getForEntity("http://localhost:8080/book/"+bookId, BookDTO.class);
		//book was not found - starting the user compensation transaction...
		if (bookResponseEntity.getStatusCodeValue()!=200) {
			compensateMaxLending(user);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		BookDTO book = bookResponseEntity.getBody();
		//book out of stock - starting the user compensation transaction...
		if (book.getStock()==0){
			compensateMaxLending(user);
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		//we update the book- substract 1 from stock
		book.setStock(book.getStock()-1);
		HttpEntity<BookDTO> bookEntity = new HttpEntity<BookDTO>(book);

		restTemplate.exchange("http://localhost:8080/book/", HttpMethod.PUT, bookEntity, BookDTO.class);

		//we can create the lending...
		Lending lending = new Lending(bookId,userId);
		Lending lendingWithId = lendingRepository.save(lending);
		return new ResponseEntity<>(lendingWithId,HttpStatus.CREATED);
	}

	private void compensateMaxLending(UserDTO user) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<UserDTO> userEntity;
		user.setMaxLending(user.getMaxLending()+1);
		userEntity = new HttpEntity<UserDTO>(user);
		restTemplate.exchange("http://localhost:8080/user/", HttpMethod.PUT, userEntity, UserDTO.class);
	}


}
