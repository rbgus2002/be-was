package application.dto.article;

import java.time.LocalDate;

public class ArticleListDto {
    private final LocalDate createDate;
    private final String username;
    private final int articleId;
    private final String title;

    public ArticleListDto(LocalDate createDate, String username, int articleId, String title) {
        this.createDate = createDate;
        this.username = username;
        this.articleId = articleId;
        this.title = title;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getUsername() {
        return username;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }
}
