package controller.annotation;

import controller.Controller;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpMethod;

public interface Handler {
    boolean matchHttpMethod(HttpMethod httpMethod);

    HttpResponse runController(Controller controller, HttpRequest httpRequest) throws Exception;
}
