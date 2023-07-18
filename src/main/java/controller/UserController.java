package controller;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.requesthandler.annotation.Controller;
import webserver.myframework.requesthandler.annotation.RequestMapping;


@Controller("/user")
public class UserController {
    @RequestMapping("/create")
    public void signUp(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String userId = getParameter(httpRequest, "userId");
            String password = getParameter(httpRequest, "password");
            String name = getParameter(httpRequest, "name");
            String email = getParameter(httpRequest, "email");
            User user = new User(userId, password, name, email);
            Database.addUser(user);
        } catch (IllegalArgumentException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }

        httpResponse.sendRedirection("/index.html");
    }

    private static String getParameter(HttpRequest httpRequest, String parameterName) {
        return httpRequest.getParameter(parameterName)
                .orElseThrow(IllegalArgumentException::new);
    }
}
