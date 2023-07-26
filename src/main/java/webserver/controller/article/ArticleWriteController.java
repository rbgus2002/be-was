package webserver.controller.article;

import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import db.SessionDatabase;
import webserver.utils.CookieConstants;
import webserver.utils.HttpField;
import webserver.utils.Location;

import java.io.IOException;

public class ArticleWriteController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String sessionId = httpRequest.getCookie().get(CookieConstants.SESSION_ID);

        httpResponse.setStatus(HttpStatus.FOUND);
        setRedirectLocation(httpResponse, sessionId);
    }

    private void setRedirectLocation(HttpResponse httpResponse, String sessionId) {
        if (isLoginStatus(sessionId)) {
            httpResponse.set(HttpField.LOCATION, Location.ARTICLE_WRITE_PAGE);
            return;
        }
        httpResponse.set(HttpField.LOCATION, Location.LOGIN_PAGE);
    }

    private boolean isLoginStatus(String sessionId) {
        return SessionDatabase.verifySessionId(sessionId);
    }
}
