package was.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
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

	public String getWriter() {
		return writer;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public String getCreatedAtToString() {
		return createdAt.format(formatter);
	}
}
