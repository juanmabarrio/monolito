package es.urjc.code.lendingService.consuming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	private long id;
	private String name;
	private int maxLending;

	public UserDTO() {
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

	public UserDTO(String name, int maxLending) {
		this.name = name;
		this.maxLending = maxLending;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name=" + name +
				", maxLending=" + maxLending +
				'}';
	}


}
