package domain.post;

import domain.user.User;

import java.time.LocalDateTime;

public class Post {
    private final User user;
    private final String title;
    private final String contents;
    private final LocalDateTime dateTime;

    public Post(User user, String title, String contents, LocalDateTime dateTime) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.dateTime = dateTime;
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
