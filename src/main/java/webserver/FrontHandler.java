package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handlers.Handler;
import webserver.http.message.Cookie;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.session.SessionManager;

public class FrontHandler {
    public static final Logger logger = LoggerFactory.getLogger(FrontHandler.class);
    private static final SessionManager sessionManager = new SessionManager();
    private static final HandlerMapper findHandler = new HandlerMapper();


    public HttpResponse handle(HttpRequest httpRequest) {
        Session session = getSession(httpRequest);
        Handler handler = findHandler.findHandler(httpRequest);
        try {
            return handler.handle(httpRequest, session);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return HttpResponse.internalServerError();
        }
    }

    private Session getSession(HttpRequest httpRequest) {
        Cookie cookie = httpRequest.getCookie();

        String sessionId = cookie.getSessionId();

        return sessionManager.getSession(sessionId);
    }
}
