package webserver;

import config.UserConfig;
import webserver.handlers.*;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.utils.FileNameScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HandlerMapper {
    public static final String STATIC_PATH = "src/main/resources/static";
    private final Map<HandlerKey, Handler> handlerTable = new HashMap<>();

    {
        handlerTable.put(new HandlerKey(HttpMethod.GET, "/index.html"), new IndexHandler());
        handlerTable.put(new HandlerKey(HttpMethod.GET, "/user/login.html"), new LoginPageHandler());
        handlerTable.put(new HandlerKey(HttpMethod.POST, "/user/login"), new LoginHandler(UserConfig.getUserService()));
        handlerTable.put(new HandlerKey(HttpMethod.GET, "/user/form.html"), new UserFormHandler());
        handlerTable.put(new HandlerKey(HttpMethod.GET, "/user/list.html"), new UserListHandler(UserConfig.getUserService()));
        handlerTable.put(new HandlerKey(HttpMethod.POST, "/user/create"), new UserJoinHandler(UserConfig.getUserService()));
        addStaticFilesRecords();
    }

    private void addStaticFilesRecords() {
        StaticFileHandler staticFileHandler = new StaticFileHandler();
        List<String> filePaths = FileNameScanner.scan(STATIC_PATH);
        filePaths.forEach(fileName -> handlerTable.put(new HandlerKey(HttpMethod.GET, fileName), staticFileHandler));
    }

    public Handler findHandler(HttpRequest httpRequest) {
        return handlerTable.getOrDefault(
                new HandlerKey(httpRequest.getMethod(), httpRequest.getURL().getPath()),
                new NotFoundHandler());
    }

    private static class HandlerKey {
        private final HttpMethod method;
        private final String path;

        public HandlerKey(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HandlerKey that = (HandlerKey) o;
            return method == that.method && Objects.equals(path, that.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(method, path);
        }
    }
}
