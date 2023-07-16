package controller;

import modelview.ModelView;
import utils.HttpRequest;

public class StaticController implements Controller {
    @Override
    public ModelView process(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        return new ModelView(path, null);
    }
}