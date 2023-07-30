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

	public static Article getArticleAt(int index) {
		if (index < articles.size()) {
			return articles.get(index);
		}
		throw new IllegalArgumentException("존재하지 않는 Article을 조회할 수 없습니다.");
	}
}