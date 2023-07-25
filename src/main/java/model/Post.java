package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Post {
    public static final String POSTID = "postId";
    public static final String USERID = "userId";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    private static final AtomicInteger postCount = new AtomicInteger(1);

    private final int postId;
    private final String userId;
    private final String title;
    private final String content;

    public Post(String userId, String title, String content) {
        this.postId = postCount.getAndIncrement();
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
