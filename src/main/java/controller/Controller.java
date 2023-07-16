package controller;

import modelview.ModelView;
import utils.HttpRequest;

public interface Controller {

    public ModelView process(HttpRequest httpRequest);
}