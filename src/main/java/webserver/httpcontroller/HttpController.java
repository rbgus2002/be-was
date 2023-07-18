package webserver.httpcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public abstract class HttpController {
    static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    public abstract String process(HttpRequest request, HttpResponse response) throws IOException;
}
