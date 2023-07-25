package controller;

import controller.annotaion.GetMapping;
import service.UserService;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private final UserService userService = new UserService();

    @GetMapping(path = "/user/create")
    public HttpResponse createUser(Map<String, String> queryString) throws IOException {
        UserService.registerUser(queryString);

        return HttpResponse.redirect("/index.html");
    }
}
