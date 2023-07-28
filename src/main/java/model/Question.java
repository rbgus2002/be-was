package model;

import java.util.Map;

public class Question {
    String writer;
    String title;
    String contents;

    public static Question fromMap(Map<String, String> map) {
        return new Question(map.get("writer"), map.get("title"), map.get("contents"));
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
