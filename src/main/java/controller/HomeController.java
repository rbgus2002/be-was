package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;


public class HomeController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        logger.info("execute");
        return "/index.html";
    }
}
