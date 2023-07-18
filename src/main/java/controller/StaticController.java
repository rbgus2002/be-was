package controller;

import modelview.ModelView;
import common.http.HttpRequest;

public class StaticController implements Controller {
    @Override
    public ModelView process(HttpRequest request) {
        return new ModelView(request.getPath());
    }

    @Override
    public void validate(HttpRequest request) {}
}