package es.urjc.code.lendingService.consuming;

public class BookDTO {

	private long id;

	private String title;
	private String author;
	private Integer stock;

	public BookDTO(String title, String author, Integer stock ) {
		this.title = title;
		this.author = author;
		this.stock = stock;
	}

	public BookDTO(){

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

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title  +
				", author=" + author +
				", stock=" + stock +
				'}';
	}

}
