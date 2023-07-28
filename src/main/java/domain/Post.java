package domain;

import java.time.LocalDateTime;

public class Post {

    private String postId;
    private final User user;
    private String title;
    private String contents;
    private final LocalDateTime dateTime;

    public Post(User user, String title, String contents, LocalDateTime dateTime) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.dateTime = dateTime;
    }

    public String getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

}
