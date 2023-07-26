package controller;

import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import service.UserService;
import webserver.http.request.HttpRequest;
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
    public HttpResponse createUser(HttpRequest request) throws IOException {
        Map<String, String> body = request.getBodyMap();
        userService.registerUser(body);

        return HttpResponse.redirect("/index.html");
    }

    @PostMapping(path = "/user/login")
    public HttpResponse signInUser(HttpRequest request) throws IOException {
        Map<String, String> headers = request.getHeadersMap();
        Map<String, String> body = request.getBodyMap();

        String userId = body.get("userId");
        String password = body.get("password");

        if (!userService.existUser(userId, password)) {
            return HttpResponse.redirect("/user/login_failed.html");
        }

        userService.signIn(headers, body);
        return null;
    }
}
