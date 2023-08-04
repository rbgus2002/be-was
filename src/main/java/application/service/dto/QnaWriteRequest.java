package application.service.dto;

public class QnaWriteRequest {
    private final String writer;
    private final String title;
    private final String contents;

    public QnaWriteRequest(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
