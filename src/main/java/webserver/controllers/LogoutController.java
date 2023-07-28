package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import model.Session;

import static service.SessionService.getSession;
import static service.SessionService.removeSession;
import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.HttpResponseStatus.FOUND;

@RequestPath(path = "/user/logout")
public class LogoutController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();

        if (isValidCookie(request.cookie())) {
            Session session = getSession(request.cookie().getSessionId());
            builder.sessionId(session.getSessionId()+";Max-Age=0");
            removeSession(session);
        }

        return builder.version(request.version())
                .status(FOUND)
                .redirect("/index.html")
                .build();
    }
}
