package model;

import java.time.LocalDate;

public class Content {
    private Long contentId;
    private LocalDate localDate;
    private String writer;
    private String title;
    private String contents;

    public Content(String writer, String title, String contents) {
        localDate = LocalDate.now();
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

    public LocalDate getLocalDate() {
        return localDate;
    }
}
