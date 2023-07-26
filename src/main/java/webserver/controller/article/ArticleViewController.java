package webserver.controller.article;

import db.ArticleDatabase;
import model.Article;
import webserver.controller.Controller;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import db.SessionDatabase;
import webserver.utils.CookieConstants;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;

import java.io.IOException;

public class ArticleViewController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String sessionId = httpRequest.getCookie().get(CookieConstants.SESSION_ID);

        if (isLoginStatus(sessionId)) {
            setHttpResponseWithArticleViewHtml(httpRequest, httpResponse);
            return;
        }
        redirectToLoginPage(httpResponse);
    }

    private boolean isLoginStatus(String sessionId) {
        return SessionDatabase.verifySessionId(sessionId);
    }

    private void setHttpResponseWithArticleViewHtml(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Article article = findArticle(httpRequest);
        String articleViewHtml = createArticleViewHtml(article);
        setHttpResponseWithHtml(httpResponse, articleViewHtml);
    }

    private void setHttpResponseWithHtml(HttpResponse httpResponse, String html) {
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private Article findArticle(HttpRequest httpRequest) {
        HttpParameters requestParameters = httpRequest.getParameters();
        int articleId = Integer.parseInt(requestParameters.get("articleId"));
        return ArticleDatabase.findByArticleId(articleId);
    }

    private String createArticleViewHtml(Article article) throws IOException {
        String templateHtml = getTemplateHtml();

        return templateHtml
                .replace("${title}", article.getTitle())
                .replace("${username}", article.getUsername())
                .replace("${createDate}", article.getCreateDate().toString())
                .replace("${contents}", article.getContents());
    }

    private String getTemplateHtml() throws IOException {
        byte[] bytes = FileUtils.readFileBytes("/article/show.html");
        return new String(bytes);
    }

    private void redirectToLoginPage(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/user/login.html");
    }
}
