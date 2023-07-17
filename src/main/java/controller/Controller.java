package controller;

import modelview.ModelView;
import common.HttpRequest;

public interface Controller {
    ModelView process(HttpRequest request);

    void validate(HttpRequest request);
}