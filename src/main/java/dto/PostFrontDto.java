package dto;

import model.Post;

import java.time.LocalDateTime;

@SuppressWarnings("FieldCanBeLocal")
public class PostFrontDto {
    private final Long postId;
    private final String title;
    private final LocalDateTime time;
    private final String author;
    private final String content;

    public PostFrontDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.time = post.getTime();
        this.author = post.getAuthor();
        this.content = post.getContent();
    }

    public String getTitle() {
        return title;
    }
}
