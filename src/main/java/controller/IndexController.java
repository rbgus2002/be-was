package controller;

import modelview.ModelView;
import utils.HttpRequest;

import static utils.HttpRequest.Method.isGetMethod;

public class IndexController implements Controller {
    @Override
    public ModelView process(HttpRequest httpRequest) {
        validateHttpRequest(httpRequest);
        return new ModelView("/templates/index.html", null);
    }

    private void validateHttpRequest(HttpRequest httpRequest) {
        if (isGetMethod(httpRequest.getMethod())) {
            return;
        }
        throw new IllegalArgumentException("잘못된 메서드");
    }
}