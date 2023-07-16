package controller;

import db.Database;
import modelview.ModelView;
import model.User;
import utils.HttpRequest;

import java.util.Map;

import static utils.HttpRequest.Method.isGetMethod;

public class CreateUserController implements Controller {
    @Override
    public ModelView process(HttpRequest httpRequest) {
        validateHttpRequest(httpRequest);
        User user = createUser(httpRequest.getParams());
        Database.addUser(user);
        return new ModelView("/templates/index.html", user);
    }

    private User createUser(Map<String, String> params) {
        return new User(
            params.get("userId"),
            params.get("password"),
            params.get("name"),
            params.get("email")
        );
    }

    private void validateHttpRequest(HttpRequest httpRequest) {
        if (isGetMethod(httpRequest.getMethod())) {
            return;
        }
        throw new IllegalArgumentException("잘못된 메서드");
    }
}