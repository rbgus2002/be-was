package controller;

import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import model.User;
import service.UserService;
import webserver.SessionManager;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private final UserService userService = new UserService();
    private final SessionManager sessionManager = new SessionManager();

    @GetMapping(path = "/index.html")
    public HttpResponse home(HttpRequest request) throws IOException {
        String sessionId = request.getCookie();

        Map<String, String> attribute = new HashMap<>();
        attribute.put("${userName}", "");
        attribute.put("${login}", "");
        attribute.put("${logout}", "style=\"display:none;\"");

        if (sessionId != null) {
            User user = (User) sessionManager.getObject(sessionId.split("=")[1]);
            attribute.put("${userName}", "<li style=\"pointer-events: none;\" ><a>" + user.getName() + "님</a></li>");
            attribute.put("${login}", "style=\"display:none;\"");
            attribute.put("${logout}", "");
        }

        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.OK)
                .path("/index.html")
                .setAttribute(attribute)
                .build();
    }

    @GetMapping(path = "/user/form.html")
    public HttpResponse form(HttpRequest request) throws IOException {
        String sessionId = request.getCookie();

        Map<String, String> attribute = new HashMap<>();
        attribute.put("${userName}", "");
        attribute.put("${login}", "");
        attribute.put("${logout}", "style=\"display:none;\"");

        if (sessionId != null) {
            User user = (User) sessionManager.getObject(sessionId.split("=")[1]);
            attribute.put("${userName}", "<li style=\"pointer-events: none;\" ><a>" + user.getName() + "님</a></li>");
            attribute.put("${login}", "style=\"display:none;\"");
            attribute.put("${logout}", "");
        }

        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.OK)
                .path("/user/form.html")
                .setAttribute(attribute)
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

    @GetMapping(path = "/user/login.html")
    public HttpResponse signInForm(HttpRequest request) throws IOException {
        String sessionId = request.getCookie();

        Map<String, String> attribute = new HashMap<>();
        attribute.put("${userName}", "");
        attribute.put("${login}", "");
        attribute.put("${logout}", "style=\"display:none;\"");

        if (sessionId != null) {
            User user = (User) sessionManager.getObject(sessionId.split("=")[1]);
            attribute.put("${userName}", "<li style=\"pointer-events: none;\" ><a>" + user.getName() + "님</a></li>");
            attribute.put("${login}", "style=\"display:none;\"");
            attribute.put("${logout}", "");
        }

        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.OK)
                .path("/user/login.html")
                .setAttribute(attribute)
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

        Map<String, String> attribute = new HashMap<>();
        attribute.put("${userName", "");

        User user = (User) sessionManager.getObject(sessionId);

        attribute.put("${userName}", "<li style=\"pointer-events: none;\" ><a>" + user.getName() + "님</a></li>");


        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.FOUND)
                .path("/index.html")
                .setCookie(sessionId, "/")
                .addHeaderParam("Location", "/index.html")
                .setAttribute(attribute)
                .build();
    }

    @GetMapping(path = "/user/list.html")
    public HttpResponse getUserList(HttpRequest request) throws IOException {
        String sessionId = request.getCookie();

        if (sessionId == null) {
            return new HttpResponse
                    .HttpResponseBuilder()
                    .status(HttpStatus.FOUND)
                    .path("/user/login.html")
                    .addHeaderParam("Location", "/user/login.html")
                    .build();
        }

        List<User> users = userService.findAll();

        StringBuilder userListString = new StringBuilder();
        for (int userIdx = 0; userIdx < users.size(); userIdx++) {
            User now = users.get(userIdx);
            userListString.append("<tr>");
            userListString.append(String.format("<th scope=\"row\">%s</th>", userIdx + 1));
            userListString.append(String.format("<td>%s</td>", now.getUserId()));
            userListString.append(String.format("<td>%s</td>", now.getName() + "님"));
            userListString.append(String.format("<td>%s</td>", now.getEmail()));
            userListString.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            userListString.append("</tr>");
        }

        User user = (User) sessionManager.getObject(sessionId.split("=")[1]);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("${user}", String.format("<li class=\"active\"><a href=\"../index.html\">%s</a></li>", user.getName()));
        attributes.put("${userList}", userListString.toString());


        return new HttpResponse
                .HttpResponseBuilder()
                .status(HttpStatus.OK)
                .path("/user/list.html")
                .setAttribute(attributes)
                .build();
    }
}
