package model;

import java.time.LocalDate;

public class Content {
    private Long contentId;
    private LocalDate localDate;
    private String writer;
    private String title;
    private String text;

    public Content(String writer, String title, String text) {
        localDate = LocalDate.now();
        this.writer = writer;
        this.title = title;
        this.text = text;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
