package webserver.httphandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public abstract class HttpHandler {
    static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public static HttpHandler of(String url) {
        if (url.equals("/")) {
            return new HomeHandler();
        }else if (url.equals("/user/create")) {
            return new JoinHandler();
        } else {
            return new DefaultHandler();
        }
    }

    public abstract void service(HttpRequest request, HttpResponse response) throws IOException;
}
