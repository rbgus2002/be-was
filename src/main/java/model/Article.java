package model;

import java.time.LocalDate;

public class Article {
    private static int articleIndex = 1;

    private int articleId;
    private String userId;
    private String username;
    private String title;
    private String contents;
    private LocalDate createDate;

    public Article(String userId, String username, String title, String contents, LocalDate createDate) {
        this.articleId = articleIndex++;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
