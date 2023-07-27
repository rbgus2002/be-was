package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import static db.BoardDatabase.findAllBoards;

public class Board {
    private Long index;
    private String writer;
    private String title;
    private String contents;
    private String time;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Board(String writer, String title, String contents) {
        this.index = (long) findAllBoards().size();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = sdf.format(new Date(System.currentTimeMillis()));
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

    public String getTime() {
        return time;
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
