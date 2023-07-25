package controller;

import controller.annotaion.PostMapping;
import service.UserService;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private final UserService userService = new UserService();

    @PostMapping(path = "/user/create")
    public HttpResponse createUser(Map<String, String> body) throws IOException {
        userService.registerUser(body);

        return HttpResponse.redirect("/index.html");
    }
}
