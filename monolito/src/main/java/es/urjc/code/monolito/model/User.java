package es.urjc.code.monolito.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int maxLending;

	public User() {
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLending() {
		return maxLending;
	}

	public void setMaxLending(int maxLending) {
		this.maxLending = maxLending;
	}

	public User(String name, int maxLending) {
		this.name = name;
		this.maxLending = maxLending;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}




}
