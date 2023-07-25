package db;

import model.Article;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArticleDatabase {
    private static final CopyOnWriteArrayList<Article> articles = new CopyOnWriteArrayList<>();

    public static void save(Article article) {
        articles.add(article);
    }

    public static Article findById(int articleId) {
        return articles.stream()
                .filter(article -> article.getArticleId() == articleId)
                .findFirst()
                .orElse(null);
    }

    public static List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }
}
