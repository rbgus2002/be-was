package model;

import java.time.LocalDateTime;

public class Post {
    private static long LAST_POST_ID = 0;

    private final long postId;
    private final String author;
    private final LocalDateTime time;
    private final String title;
    private final String content;

    public Post(String author, String title, String content) {
        synchronized (Post.class) {
            LAST_POST_ID++;
            this.postId = LAST_POST_ID;
        }
        this.author = author;
        this.time = LocalDateTime.now();
        this.title = title;
        this.content = content;
    }

    public long getPostId() {
        return postId;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
