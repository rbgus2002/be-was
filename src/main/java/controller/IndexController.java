package controller;

import modelview.ModelView;
import common.HttpRequest;

import static common.Method.isGetMethod;

public class IndexController implements Controller {
    @Override
    public ModelView process(HttpRequest request) {
        validate(request);
        return new ModelView("/index.html");
    }

    @Override
    public void validate(HttpRequest request) {
        if (isGetMethod(request.getMethod())) {
            return;
        }
        throw new IllegalArgumentException("잘못된 메서드");
    }
}