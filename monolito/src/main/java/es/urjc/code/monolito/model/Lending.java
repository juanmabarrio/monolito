package es.urjc.code.monolito.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Lending {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private User user;
	@OneToOne
	private Book book;



	public Lending() {
	}

	public Lending(Book book, User user) {
		this.book = book;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User author) {
		this.user = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
