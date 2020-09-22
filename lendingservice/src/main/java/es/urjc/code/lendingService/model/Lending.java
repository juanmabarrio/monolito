package es.urjc.code.lendingService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lending {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Long userId;

	private Long bookId;

	public Lending() {
	}

	public Lending(Long bookId, Long userId) {
		this.bookId = bookId;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
}
