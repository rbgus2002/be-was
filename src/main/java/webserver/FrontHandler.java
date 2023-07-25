package webserver;

import config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handlers.*;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.utils.FileNameScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FrontHandler {
    private static final Logger logger = LoggerFactory.getLogger(FrontHandler.class);
    public static final String STATIC_PATH = "src/main/resources/static";

    private final Map<RouteKey, Handler> routeTables = new HashMap<>();

    {
        routeTables.put(new RouteKey(HttpMethod.GET, "/index.html"), new IndexHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/form.html"), new UserFormHandler());
        routeTables.put(new RouteKey(HttpMethod.POST, "/user/create"), new UserJoinHandler(UserConfig.getUserService()));
        addStaticFilesRecords();
    }
    
    private void addStaticFilesRecords() {
        StaticFileHandler staticFileHandler = new StaticFileHandler();
        List<String> filePaths = FileNameScanner.scan(STATIC_PATH);
        filePaths.forEach(fileName -> routeTables.put(new RouteKey(HttpMethod.GET, fileName), staticFileHandler));
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        Handler handler = findHandler(httpRequest);
        try {
            return handler.handle(httpRequest);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return HttpResponse.internalServerError();
        }
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
