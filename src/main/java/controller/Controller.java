package controller;

import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import service.UserService;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public HttpResponse home() throws IOException {
        return HttpResponse.redirect("/index.html");
    }

    @PostMapping(path = "/user/create")
    public HttpResponse createUser(Map<String, String> body) throws IOException {
        userService.registerUser(body);

        return HttpResponse.redirect("/index.html");
    }

    @PostMapping(path = "/user/login")
    public HttpResponse signInUser(Map<String, String> body) throws IOException {
        String userId = body.get("userId");
        String password = body.get("password");

        if (!userService.existUser(userId, password)) {
            return HttpResponse.redirect("/user/login_failed.html");
        }

        // TODO: 세션 설정
    }
}
