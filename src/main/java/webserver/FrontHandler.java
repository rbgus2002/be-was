package webserver;

import config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handlers.*;
import webserver.http.message.*;
import webserver.session.Session;
import webserver.session.SessionManager;
import webserver.model.Model;
import webserver.utils.FileNameScanner;

import java.util.*;
import java.util.stream.Collectors;

import static webserver.http.message.HttpHeaders.SEMI_COLON;

public class FrontHandler {
    private static final Logger logger = LoggerFactory.getLogger(FrontHandler.class);
    private static final SessionManager sessionManager = new SessionManager();
    public static final String STATIC_PATH = "src/main/resources/static";
    public static final String COOKIE = "Cookie";

    private final Map<RouteKey, Handler> routeTables = new HashMap<>();

    {
        routeTables.put(new RouteKey(HttpMethod.GET, "/index.html"), new IndexHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/login.html"), new LoginPageHandler());
        routeTables.put(new RouteKey(HttpMethod.POST, "/user/login"), new LoginHandler(UserConfig.getUserService()));
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/form.html"), new UserFormHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/list.html"), new UserListHandler(UserConfig.getUserService()));
        routeTables.put(new RouteKey(HttpMethod.POST, "/user/create"), new UserJoinHandler(UserConfig.getUserService()));
        addStaticFilesRecords();
    }

    private void addStaticFilesRecords() {
        StaticFileHandler staticFileHandler = new StaticFileHandler();
        List<String> filePaths = FileNameScanner.scan(STATIC_PATH);
        filePaths.forEach(fileName -> routeTables.put(new RouteKey(HttpMethod.GET, fileName), staticFileHandler));
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        Session session = getSession(httpRequest);
        Model model = new Model();
        Handler handler = findHandler(httpRequest);
        try {
            HttpResponse response = handler.handle(httpRequest, session, model);
            response.setCookie(session.getId(), "/");
            return response;
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
            List<String> cookies = Arrays.stream(cookieString.split(SEMI_COLON)).collect(Collectors.toList());
            return Cookie.from(cookies);
        }
        return Cookie.from(List.of());
    }

    private Handler findHandler(HttpRequest httpRequest) {
        return routeTables.getOrDefault(
                new RouteKey(httpRequest.getMethod(), httpRequest.getURL().getPath()),
                new NotFoundHandler());
    }

    private static class RouteKey {
        private final HttpMethod method;
        private final String path;

        public RouteKey(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RouteKey routeKey = (RouteKey) o;
            return method == routeKey.method && Objects.equals(path, routeKey.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method, path);
        }
    }
}
