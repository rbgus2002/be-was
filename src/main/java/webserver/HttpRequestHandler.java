package webserver;

import config.UserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.handlers.*;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    private final Map<RouteKey, Handler> routeTables = new HashMap<>();

    {
        routeTables.put(new RouteKey(HttpMethod.GET, "/index.html"), new IndexHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/form.html"), new UserFormHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/create"), new UserJoinHandler(UserConfig.getUserService()));
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        Handler handler = routeTables.getOrDefault(
                new RouteKey(httpRequest.getMethod(), httpRequest.getURL().getPath()),
                new NotFoundHandler());
        return handler.handle(httpRequest);
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
