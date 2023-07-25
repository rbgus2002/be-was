package model;

public class Post {

    private static Long NEXT_POST_ID = 1L;
    private final Long id;
    private String title;
    private String writer;
    private String contents;

    public Post(String title, String writer, String contents) {
        //TODO: Lock
        this.id = NEXT_POST_ID++;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
