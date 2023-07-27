package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Article;

public class ArticleDatabase {
	private static List<Article> articles = Collections.synchronizedList(new ArrayList<>());

	public static void addArticle(Article article) {
		articles.add(article);
	}

	public static List<Article> getArticles() {
		return Collections.unmodifiableList(articles);
	}
}