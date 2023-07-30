package application.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Qna {
    private static final AtomicInteger increment = new AtomicInteger(0);

    private final int id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime localDateTime;

    public Qna(final String writer, final String title, final String contents) {
        this.id = increment.getAndIncrement();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        localDateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
