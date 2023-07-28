package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Post {
    public static final String POST_ID = "postId";
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    private static final AtomicInteger postCount = new AtomicInteger(1);

    private int postId;
    private final String userId;
    private final String title;
    private final String content;

    public Post(String userId, String title, String content) {
        this.postId = postCount.getAndIncrement();
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public int setPostId(int postId) {
        this.postId = postId;
        return this.postId;
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
