package model;

public class Article {
	private String title;
	private String writer;
	private String contents;

	private Article(final String title, final String writer, final String contents) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
	}

	public static Article of(final String title, final String writer, final String contents) {
		return new Article(title, writer, contents);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(final String writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(final String contents) {
		this.contents = contents;
	}
}
