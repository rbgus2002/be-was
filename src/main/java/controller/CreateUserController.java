package controller;

import model.User;
import modelview.ModelView;
import service.UserService;
import common.HttpRequest;

import java.util.Map;

import static common.HttpRequest.Method.isGetMethod;

public class CreateUserController implements Controller {
    @Override
    public ModelView process(HttpRequest request) {
        validate(request);

        Map<String, String> params = request.getParams();
        User user = UserService.createUser(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );

        ModelView modelView = new ModelView("/index.html");
        modelView.getModel().put("user", user);
        return modelView;
    }

    @Override
    public void validate(HttpRequest request) {
        if (isGetMethod(request.getMethod())) {
            return;
        }
        throw new IllegalArgumentException("잘못된 메서드");
    }
}