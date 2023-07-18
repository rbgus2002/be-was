package controller;

import modelview.ModelView;
import common.http.HttpRequest;

public interface Controller {
    ModelView process(HttpRequest request);

    void validate(HttpRequest request);
}