package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ForwardController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(ForwardController.class);
    private final String url;
    public ForwardController(String url) {
        this.url = url;
    }

    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        logger.info("execute");
        return url;
    }
}
