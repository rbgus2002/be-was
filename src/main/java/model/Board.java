package model;

import static db.BoardDatabase.findAllBoards;

public class Board {
    private Long index;
    private String writer;
    private String title;
    private String contents;

    public Board(String writer, String title, String contents) {
        this.index = (long) findAllBoards().size();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getIndex() {
        return index;
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

    @Override
    public String toString() {
        return "Board{" +
                "index=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
