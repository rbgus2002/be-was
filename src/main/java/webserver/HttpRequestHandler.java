package webserver;

import webserver.handlers.Handler;
import webserver.handlers.IndexHandler;
import webserver.handlers.NotFoundHandler;
import webserver.handlers.UserFormHandler;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHandler {
    private final Map<RouteKey, Handler> routeTables = new HashMap<>();

    {
        routeTables.put(new RouteKey(HttpMethod.GET, "/index.html"), new IndexHandler());
        routeTables.put(new RouteKey(HttpMethod.GET, "/user/form.html"), new UserFormHandler());
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
