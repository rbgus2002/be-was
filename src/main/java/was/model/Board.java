package was.model;

import java.time.LocalDateTime;

public class Board {

	private String name;
	private LocalDateTime createdAt;
	private User user;
	private String text;

	public Board(String name, LocalDateTime createdAt, User user, String text) {
		this.name = name;
		this.createdAt = createdAt;
		this.user = user;
		this.text = text;
	}
}
