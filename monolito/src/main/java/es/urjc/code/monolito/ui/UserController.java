package es.urjc.code.monolito.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import es.urjc.code.monolito.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.urjc.code.monolito.model.User;
import es.urjc.code.monolito.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostConstruct
	public void init() {

	}
	
	@GetMapping("/user")
	public List<User> allUsers() {
		return userRepo.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User user(@PathVariable long id) {
		return userRepo.findById(id).get();
	}


	@PostMapping("/user")
	@ResponseStatus(code = HttpStatus.CREATED)
	public User newUser(@RequestBody User user) {
		User userWithId = userRepo.save(user);
		logger.info("new user created -> " + userWithId );
		return userWithId;
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable long id) {
		User user = userRepo.findById(id).orElse(null);
		if (user == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}
