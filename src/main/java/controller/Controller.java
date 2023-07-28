package controller;

import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import service.UserService;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private final UserService userService = new UserService();

    @GetMapping(path = "/")
    public HttpResponse home() throws IOException {
        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.FOUND)
                .addHeaderParam("Location", "/index.html")
                .path("/index.html")
                .build();
    }

    @PostMapping(path = "/user/create")
    public HttpResponse createUser(HttpRequest request) throws IOException {
        Map<String, String> body = request.getBodyMap();
        userService.registerUser(body);

        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.FOUND)
                .path("/index.html")
                .addHeaderParam("Location", "/index.html")
                .build();
    }

    @PostMapping(path = "/user/login")
    public HttpResponse signInUser(HttpRequest request) throws IOException {
        Map<String, String> body = request.getBodyMap();

        String userId = body.get("userId");
        String password = body.get("password");

        if (!userService.existUser(userId, password)) {
            return new HttpResponse
                    .HttpResponseBuilder()
                    .status(HttpStatus.FOUND)
                    .path("/user/login_failed.html")
                    .addHeaderParam("Location", "/user/login_failed.html")
                    .build();
        }

        String sessionId = userService.signIn(userId);
        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.FOUND)
                .path("/index.html")
                .setCookie(sessionId, "/")
                .addHeaderParam("Location", "/index.html")
                .build();
    }
}
