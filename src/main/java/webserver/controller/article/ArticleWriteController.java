package webserver.controller.article;

import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.SessionManager;
import webserver.utils.HttpField;

import java.io.IOException;

public class ArticleWriteController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String sessionId = httpRequest.getCookie().get("sid");

        httpResponse.setStatus(HttpStatus.FOUND);
        checkLoginStatusAndSetLocation(httpResponse, sessionId);
    }

    private void checkLoginStatusAndSetLocation(HttpResponse httpResponse, String sessionId) {
        if (SessionManager.verifySessionId(sessionId)) {
            httpResponse.set(HttpField.LOCATION, "/article/write.html");
            return;
        }
        httpResponse.set(HttpField.LOCATION, "/user/login.html");
    }
}
