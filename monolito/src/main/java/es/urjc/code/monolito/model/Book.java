package es.urjc.code.monolito.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;
	private String author;
	private Integer stock;

	public Book(String title, String author, Integer stock ) {
		this.title = title;
		this.author = author;
		this.stock = stock;
	}

	public Book(){

	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}


}
