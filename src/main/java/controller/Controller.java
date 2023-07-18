package controller;

import annotation.GetMapping;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.request.HttpRequest;

import java.util.Map;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping(value = "/index.html")
    public void mainPage()
    {
        logger.debug("Get Mapping Call Success!!");
    }


    @GetMapping(value = "users/create")
    public User createUser(){
        logger.debug("GET users/create success!");

        return null;
    }
}
