package webserver.httpcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public abstract class HttpController {
    static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    public static HttpController of(String url) {
        if (url.equals("/")) {
            return new HomeController();
        } else if (url.equals("/user/create")) {
            return new JoinController();
        } else {
            return new DefaultController();
        }
    }

    public abstract void process(HttpRequest request, HttpResponse response) throws IOException;
}
