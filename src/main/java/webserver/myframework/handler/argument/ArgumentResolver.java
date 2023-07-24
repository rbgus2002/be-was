package webserver.myframework.handler.argument;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.lang.reflect.Method;

public interface ArgumentResolver {
    Object[] resolve(Method method, HttpRequest httpRequest, HttpResponse httpResponse) throws IllegalArgumentException;
}
