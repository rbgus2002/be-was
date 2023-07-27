package was.model;

import java.time.LocalDateTime;

public class Board {

	private String writer;
	private LocalDateTime createdAt;
	private String title;
	private String contents;

	public Board(String writer, String title, String contents) {
		this.writer = writer;
		this.createdAt = LocalDateTime.now();
		this.title = title;
		this.contents = contents;
	}
}
