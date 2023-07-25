package webserver.controller.index;

import db.ArticleDatabase;
import db.UserDatabase;
import model.Article;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.SessionManager;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;

import java.io.IOException;

public class IndexPageController implements Controller {
    private final String articleInfoTemplateHtml = "<tr>\n"
                    + "<td>${createDate}</td>\n"
                    + "<td>${username}</td>\n"
                    + "<td><a href=\"/article/view?articleId=${articleId}\">${title}</a></td>\n"
                    + "</tr>\n";

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String indexPageHtml = createIndexPageHtml(httpRequest);
        setHttpResponseWithHtml(httpResponse, indexPageHtml);
    }

    private String createIndexPageHtml(HttpRequest httpRequest) throws IOException {
        String templateHtml = getTemplateHtml();

        if (isLoginStatus(httpRequest)) {
            return createUserIndexPageHtml(templateHtml, getUsername(httpRequest));
        }
        return createGuestIndexPageHtml(templateHtml);
    }

    private String createUserIndexPageHtml(String templateHtml, String username) {
        String loginButton = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String userNameDisplayButton = "<li><a href=\"#\" role=\"button\">" + username + "</a></li>";

        String userIndexPageHtml = templateHtml
                .replace(loginButton, userNameDisplayButton);

        return fillArticleListIntoHtml(userIndexPageHtml);
    }

    private String createGuestIndexPageHtml(String templateHtml) {
        return fillArticleListIntoHtml(templateHtml);
    }

    private String fillArticleListIntoHtml(String html) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Article article : ArticleDatabase.findAll()) {
            appendArticleInfoHtml(stringBuilder, article);
        }

        String articleListHtml = stringBuilder.toString();

        return html.replace("${articleList}", articleListHtml);
    }

    private void appendArticleInfoHtml(StringBuilder stringBuilder, Article article) {
        String articleInfoHtml = articleInfoTemplateHtml
                .replace("${createDate}", article.getCreateDate().toString())
                .replace("${username}", article.getUsername())
                .replace("${articleId}", String.valueOf(article.getArticleId()))
                .replace("${title}", article.getTitle());

        stringBuilder.append(articleInfoHtml);
    }

    private String getTemplateHtml() throws IOException {
        byte[] bytes = FileUtils.readFileBytes("/index.html");
        return new String(bytes);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie().get("sid");
        return SessionManager.verifySessionId(sessionId);
    }

    private String getUsername(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie().get("sid");
        String userId = SessionManager.findUserIdBySessionId(sessionId);
        return UserDatabase.findUserById(userId).getName();
    }

    private void setHttpResponseWithHtml(HttpResponse httpResponse, String html) {
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }
}
