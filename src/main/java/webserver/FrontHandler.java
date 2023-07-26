package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handlers.Handler;
import webserver.http.message.Cookie;
import webserver.http.message.HttpHeaders;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.session.SessionManager;

import java.util.Arrays;
import java.util.List;

import static webserver.http.message.HttpHeaders.COOKIE;
import static webserver.http.message.HttpHeaders.SEMI_COLON;

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
        Cookie cookie = getCookie(httpRequest);

        String sessionId = cookie.getSessionValue();

        return sessionManager.getSession(sessionId);
    }

    private Cookie getCookie(HttpRequest httpRequest) {
        HttpHeaders headers = httpRequest.getHttpHeaders();
        if (headers.contains(COOKIE)) {
            String cookieString = headers.getSingleValue(COOKIE);
            List<String> cookies = Arrays.asList(cookieString.split(SEMI_COLON));
            return Cookie.from(cookies);
        }
        return Cookie.from(List.of());
    }
}
