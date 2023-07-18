package user.controller;

import db.Database;
import model.User;
import user.service.UserService;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.requesthandler.annotation.Controller;
import webserver.myframework.requesthandler.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = HttpMethod.POST)
    public void signUp(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> parameterMap = getParameterMap(httpRequest.getBodyToString());
        try {
            verifySignUpParameters(parameterMap);
            userService.signUp(
                    parameterMap.get("userId"),
                    parameterMap.get("password"),
                    parameterMap.get("name"),
                    parameterMap.get("email"));
        } catch (IllegalArgumentException exception) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }

        httpResponse.sendRedirection("/index.html");
    }

    private static void verifySignUpParameters(Map<String, String> parameterMap) {
        if (parameterMap.values().size() != 4 ||
            parameterMap.values().stream()
                    .anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }

    private static Map<String, String> getParameterMap(String body) {
        Map<String, String> parameterMap = new HashMap<>();
        String[] parameters = body.split("&");
        for (String parameter : parameters) {
            String[] keyValue = parameter.split("=");
            parameterMap.put(keyValue[0].trim(), keyValue[0].trim());
        }
        return parameterMap;
    }
}
