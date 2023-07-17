package controller;

import common.HttpRequest;
import modelview.ModelView;

public class ErrorController implements Controller {
    @Override
    public ModelView process(HttpRequest request) {
        return new ModelView("/error.html");
    }

    @Override
    public void validate(HttpRequest request) {}
}