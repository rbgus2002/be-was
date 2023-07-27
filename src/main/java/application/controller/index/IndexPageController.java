package application.controller.index;

import application.controller.Controller;
import application.dto.article.ArticleListDto;
import application.service.ArticleService;
import application.service.SessionService;
import application.service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.CookieConstants;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;
import webserver.utils.Location;

import java.io.IOException;

public class IndexPageController implements Controller {
    private final ArticleService articleService = ArticleService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();
    private final UserService userService = UserService.getInstance();

    private static final String articleInfoTemplateHtml = "<tr>\n"
            + "<td>${createDate}</td>\n"
            + "<td>${username}</td>\n"
            + "<td><a href=\"/article/view?articleId=${articleId}\">${title}</a></td>\n"
            + "</tr>\n";

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String indexPageHtml = createIndexPageHtml(httpRequest);
        setHttpResponseWithIndexPageHtml(httpResponse, indexPageHtml);
    }

    private String createIndexPageHtml(HttpRequest httpRequest) throws IOException {
        String templateHtml = getTemplateHtml();

        if (isLoginStatus(httpRequest)) {
            return createUserIndexPageHtml(templateHtml, getUsername(httpRequest));
        }
        return createGuestIndexPageHtml(templateHtml);
    }

    private String createUserIndexPageHtml(String templateHtml, String username) {
        String userIndexPageHtml = substituteLoginTextWithUserName(templateHtml, username);
        return appendArticleListHtmlInto(userIndexPageHtml);
    }

    private String substituteLoginTextWithUserName(String templateHtml, String username) {
        String loginButton = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>";
        String userNameDisplayButton = "<li><a href=\"#\" role=\"button\">" + username + "</a></li>";
        return templateHtml.replace(loginButton, userNameDisplayButton);
    }

    private String createGuestIndexPageHtml(String templateHtml) {
        return appendArticleListHtmlInto(templateHtml);
    }

    private String appendArticleListHtmlInto(String html) {
        StringBuilder stringBuilder = new StringBuilder();

        for (ArticleListDto dto : articleService.getList()) {
            appendArticleInfoHtml(stringBuilder, dto);
        }

        String articleListHtml = stringBuilder.toString();

        return html.replace("${articleList}", articleListHtml);
    }

    private void appendArticleInfoHtml(StringBuilder stringBuilder, ArticleListDto dto) {
        String articleInfoHtml = articleInfoTemplateHtml
                .replace("${createDate}", dto.getCreateDate().toString())
                .replace("${username}", dto.getUsername())
                .replace("${articleId}", String.valueOf(dto.getArticleId()))
                .replace("${title}", dto.getTitle());

        stringBuilder.append(articleInfoHtml);
    }

    private String getTemplateHtml() throws IOException {
        byte[] bytes = FileUtils.readFileBytes(Location.INDEX_PAGE);
        return new String(bytes);
    }

    private boolean isLoginStatus(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(CookieConstants.SESSION_ID);
        return sessionService.verifySessionId(sessionId);
    }

    private String getUsername(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(CookieConstants.SESSION_ID);
        String userId = sessionService.findUserId(sessionId);
        return userService.findNameById(userId);
    }

    private void setHttpResponseWithIndexPageHtml(HttpResponse httpResponse, String html) {
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }
}
