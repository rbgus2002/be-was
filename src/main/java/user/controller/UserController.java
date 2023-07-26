package user.controller;

import db.UserTable;
import model.User;
import user.service.UserService;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.handler.argument.annotation.RequestBody;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.handler.request.annotation.RequestMapping;
import webserver.myframework.model.Model;
import webserver.myframework.session.Session;

import java.util.*;


@SuppressWarnings("unused")
@Controller("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = HttpMethod.POST)
    public void signUp(@RequestBody String body, HttpResponse httpResponse) {
        try {
            Map<String, String> parameterMap = getParameterMap(body, 4);
            userService.signUp(
                    parameterMap.get("userId"),
                    parameterMap.get("password"),
                    parameterMap.get("name"),
                    parameterMap.get("email"));
            httpResponse.sendRedirection("/index.html");
        } catch (IllegalArgumentException exception) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public void signIn(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Map<String, String> parameterMap = getParameterMap(httpRequest.getBodyToString(), 2);
            User user = userService.signIn(parameterMap.get("userId"), parameterMap.get("password"));
            if (user == null) {
                httpResponse.sendRedirection("/user/login_failed.html");
                return;
            }
            Session session = httpRequest.getSession();
            session.setAttribute("userId", user.getUserId());
            httpResponse.sendRedirection("/index.html");
        } catch (IllegalArgumentException exception) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/list")
    public void listUsers(HttpRequest httpRequest, HttpResponse httpResponse, Model model) {
        Session session = httpRequest.getSession(false);
        if(session == null) {
            httpResponse.sendRedirection("/user/login.html");
            return;
        }
        User user = UserTable.findUserById((String) session.getAttribute("userId"));
        model.addParameter("user", user);
        model.addParameter("users", new ArrayList<>(UserTable.findAll()));
        httpResponse.setUri("/user/list.html");
    }

    private static void verifyParameters(Map<String, String> parameterMap, int size) {
        if (parameterMap.values().size() != size ||
            parameterMap.values().stream()
                    .anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }

    private static Map<String, String> getParameterMap(String body, int size) {
        Map<String, String> parameterMap = new HashMap<>();
        for (String parameter : body.split("&")) {
            String[] keyValue = parameter.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException();
            }
            parameterMap.put(keyValue[0].trim(), keyValue[1].trim());
        }

        verifyParameters(parameterMap, size);
        return parameterMap;
    }
}
