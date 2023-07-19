package webserver.controller;

import db.Database;
import model.User;
import utils.Parser;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class UserController implements Controller {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> queryParams = Parser.parseQueryParameters(httpRequest.getRequestUri());
        String userId = queryParams.get("userId");
        String password = queryParams.get("password");
        String name = queryParams.get("name");
        String email = queryParams.get("email");

        if (userId == null || password == null || name == null || email == null) {
            throw new IllegalArgumentException("입력을 제대로 해주세요!");
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }
}
