package controller;

import modelview.ModelView;
import common.http.HttpRequest;

import static common.enums.Method.isGetMethod;

public class GetUserFormController implements Controller {
    @Override
    public ModelView process(HttpRequest request) {
        validate(request);
        return new ModelView("/user/form.html");
    }

    @Override
    public void validate(HttpRequest request) {
        if (isGetMethod(request.getMethod())) {
            return;
        }
        throw new IllegalArgumentException("잘못된 메서드");
    }
}