package es.urjc.code.monolito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import es.urjc.code.monolito.model.Book;
import es.urjc.code.monolito.repository.BookRepository;
import es.urjc.code.monolito.repository.LendingRepository;
import es.urjc.code.monolito.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.urjc.code.monolito.model.Lending;
import es.urjc.code.monolito.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestAPITest {
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private LendingRepository lendingRepo;
	@LocalServerPort
	int port;


	@BeforeEach
	public void setUp() {
		RestAssured.port = port;

		lendingRepo.deleteAll();
		bookRepo.deleteAll();
		userRepo.deleteAll();

		User sebas = new User("sebas",2);
		User maaret = new User("maaret", 5);
		User josue = new User("josue", 5);

		userRepo.save(sebas);
		userRepo.save(maaret);
		userRepo.save(josue);

		Book elQuijote = new Book("El Quijote","Miguel De Cervantes",2);
		Book losRenglones = new Book("Los Renglones Torcidos de Dios","Ken Follet" ,5);
		Book laBiblia = new Book("La Biblia","mmm", 5);

		bookRepo.save(elQuijote);
		bookRepo.save(losRenglones);
		bookRepo.save(laBiblia);

		Lending lending = new Lending(elQuijote,sebas);
		lendingRepo.save(lending);

	}


    @Test
    public void createNewBookTest(){

        given().
                request()
                .body("{ \"title\" : \"Leche\", \"author\": \"johan milks\" , \"stock\":3}")
                .contentType(ContentType.JSON).
                when().
                post("/book").
                then().
                statusCode(201).
                body("title", equalTo("Leche"));
    }

	@Test
	public void readAllBooksTest(){
		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/book").
				then().
				statusCode(200).
				body("$",hasSize(3));
	}

	@Test
	public void deleteOneBookTest(){

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				delete("/book/5").
				then().
				statusCode(200).
				body("id",equalTo(5));
	}

	@Test
	public void updateOneBookTest(){
		Book book = bookRepo.findAll().get(0);
		Long bookId = book.getId();
		Integer stock = book.getStock();
		book.setStock(stock -1);
		given().
				request()
				.contentType(ContentType.JSON).
				body(book).
				when().
				put("/book").
				then().
				statusCode(201).
				body("id",equalTo(bookId.intValue())).
				body("stock",equalTo(stock-1));

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/book/"+bookId).
				then().
				statusCode(200).
				body("id",equalTo(bookId.intValue())).
				body("stock",equalTo(stock-1));
	}

	@Test
	public void createNewUserTest(){

		given().
				request()
				.body("{ \"name\" : \"Arnaldo Josueldo\"}")
				.contentType(ContentType.JSON).
				when().
				post("/user").
				then().
				statusCode(201).
				body("name", equalTo("Arnaldo Josueldo"));
	}
	@Test
	public void updateOneUserTest(){
		User user = userRepo.findAll().get(0);
		Long userId = user.getId();
		Integer maxLending = user.getMaxLending();
		user.setMaxLending(maxLending -1);
		given().
				request()
				.contentType(ContentType.JSON).
				body(user).
				when().
				put("/user").
				then().
				statusCode(201).
				body("id",equalTo(userId.intValue())).
				body("maxLending",equalTo(maxLending-1));

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/user/"+userId).
				then().
				statusCode(200).
				body("id",equalTo(userId.intValue())).
				body("maxLending",equalTo(maxLending-1));
	}
	@Test
	public void readAllUsersTest(){
		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/user").
				then().
				statusCode(200).
				body("$",hasSize(3));
	}

	@Test
	public void deleteOneUserTest(){
		Long userId = userRepo.findAll().get(1).getId();
		given().
				request()
				.contentType(ContentType.JSON).
				when().
				delete("/user/"+userId).
				then().
				statusCode(200).
				body("id",equalTo(userId.intValue()));
	}

	@Test
	public void createLendingTest(){
		User user = new User("juan",10);
		Book book = new Book("testBook","Juan de Mariana,",3);
		Long userId = userRepo.save(user).getId();
		Long bookId = bookRepo.save(book).getId();

		given().
				request()
				.queryParam("userId",userId)
				.queryParam("bookId",bookId)
				.contentType(ContentType.JSON).
				when().
				post("/lending").
				then().
				statusCode(201).
				body("user.id", equalTo(userId.intValue())).
				body("book.id", equalTo(bookId.intValue()));

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/book/"+bookId).
				then().
				body("stock",equalTo(2));

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/user/"+userId).
				then().
				body("maxLending",equalTo(9));




	}

	@Test
	public void createLendingTestFailureBecauseUserReachedMaxBooks(){
		User user = new User("juan",0);
		Long userId = userRepo.save(user).getId();
		Long bookId = bookRepo.findAll().get(0).getId();

		given().
				request()
				.queryParam("userId",userId)
				.queryParam("bookId",bookId)
				.contentType(ContentType.JSON).
				when().
				post("/lending").
				then().
				statusCode(403);
	}
	@Test
	public void createLendingTestFailureBecauseRunOutOfStock(){
		User user = new User("juan",10);
		Book book = new Book("testBook","Juan de Mariana,",0);
		Long userId = userRepo.save(user).getId();
		Long bookId = bookRepo.save(book).getId();

		given().
				request()
				.queryParam("userId",String.valueOf(userId))
				.queryParam("bookId",String.valueOf(bookId))
				.contentType(ContentType.JSON).
				when().
				post("/lending").
				then().
				statusCode(403);
	}

	@Test
	public void readAllLendingTest(){

		given().
				request()
				.contentType(ContentType.JSON).
				when().
				get("/user").
				then().
				statusCode(200).
				body("$",hasSize(3));
	}

	@Test
	public void deleteOneLendingTest(){
		Long id = lendingRepo.findAll().get(0).getId();
		given().
				request()
				.contentType(ContentType.JSON).
				when().
				delete("/lending/"+id).
				then().
				statusCode(200).
				body("id",equalTo(id.intValue()));
	}



}
