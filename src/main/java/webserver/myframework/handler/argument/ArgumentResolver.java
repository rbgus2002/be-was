package webserver.myframework.handler.argument;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.model.Model;

import java.lang.reflect.Method;

public interface ArgumentResolver {
    Object[] resolve(Method method, HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IllegalArgumentException;
}
