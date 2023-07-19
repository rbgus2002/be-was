package webserver.controller;

import db.Database;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

import static webserver.http.HttpStateCode.REDIRECT;

public class SignUpController implements Controller {

    @Override
    public void process(HttpRequest request, HttpResponse response) {

        Map<String, String> queryMap = request.getQueryMap();
        String userId = queryMap.get("userId");
        String password = queryMap.get("password");
        String name = queryMap.get("name");
        String email = queryMap.get("email");
        Database.addUser(new User(userId, password, name, email));

        response.setContentType(request.getPath());
        response.setStateCode(REDIRECT);
        response.setLocation("/index.html");

    }
}
