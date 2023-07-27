package application.controller.article;

import application.controller.Controller;
import application.dto.article.ArticleViewDto;
import application.service.ArticleService;
import application.service.SessionService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;
import webserver.utils.Location;

import java.io.IOException;

public class ArticleViewController implements Controller {
    private final ArticleService articleService = ArticleService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String sessionId = httpRequest.getSessionId();

        if (sessionService.verifySessionId(sessionId)) {
            setHttpResponseWithArticleViewHtml(httpRequest, httpResponse);
            return;
        }
        httpResponse.sendRedirect(Location.LOGIN_PAGE);
    }

    private void setHttpResponseWithArticleViewHtml(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        int articleId = Integer.parseInt(httpRequest.getParameter("articleId"));
        ArticleViewDto articleViewDto = articleService.getViewDto(articleId);

        String articleViewHtml = createArticleViewHtml(articleViewDto);
        setHttpResponseWithHtml(httpResponse, articleViewHtml);
    }

    private void setHttpResponseWithHtml(HttpResponse httpResponse, String html) {
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.set(HttpField.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.set(HttpField.CONTENT_LENGTH, html.length());
        httpResponse.setBody(html.getBytes());
    }

    private String createArticleViewHtml(ArticleViewDto dto) throws IOException {
        String templateHtml = FileUtils.readFileAsString(Location.ARTICLE_VIEW_PAGE);

        return templateHtml
                .replace("${title}", dto.getTitle())
                .replace("${username}", dto.getUsername())
                .replace("${createDate}", dto.getCreateDate())
                .replace("${contents}", dto.getContents());
    }
}
