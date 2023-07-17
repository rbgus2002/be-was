package controller;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.requesthandler.annotation.Controller;
import webserver.myframework.requesthandler.annotation.RequestMapping;


@Controller("/user")
public class UserController {
    @RequestMapping("/create")
    public void signUp(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);
        Database.addUser(user);

        httpResponse.sendRedirection("/index.html");
    }
}
