package webserver.controller.article;

import db.ArticleDatabase;
import db.UserDatabase;
import model.Article;
import model.User;
import webserver.controller.Controller;
import webserver.exceptions.BadRequestException;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.SessionManager;
import webserver.utils.HttpField;
import webserver.utils.HttpParametersParser;

import java.io.IOException;
import java.time.LocalDate;

public class ArticleSaveController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            String sessionId = httpRequest.getCookie().get("sid");
            HttpParameters articleParameters = HttpParametersParser.parse(httpRequest.getBody());

            checkLoginStatus(sessionId);

            addNewArticle(articleParameters, getUser(sessionId));

            redirectToIndexPage(httpResponse);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
    }

    private void checkLoginStatus(String sessionId) throws BadRequestException {
        if (!SessionManager.verifySessionId(sessionId)) {
            throw new BadRequestException();
        }
    }

    private User getUser(String sessionId) {
        String userID = SessionManager.findUserIdBySessionId(sessionId);
        return UserDatabase.findUserById(userID);
    }

    private void redirectToIndexPage(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/index.html");
    }

    private void addNewArticle(HttpParameters articleParameters, User user) {
        String userId = user.getUserId();
        String userName = user.getName();
        String title = articleParameters.get("title");
        String contents = articleParameters.get("contents");
        LocalDate createDate = LocalDate.now();

        Article article = new Article(userId, userName, title, contents, createDate);

        ArticleDatabase.save(article);
    }
}
