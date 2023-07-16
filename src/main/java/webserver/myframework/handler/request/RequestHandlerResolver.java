package webserver.myframework.handler.request;

import webserver.http.HttpMethod;
import webserver.myframework.handler.request.exception.CannotResolveHandlerException;
import webserver.myframework.handler.request.exception.DuplicateRequestHandlerException;

public interface RequestHandlerResolver {
    void registerHandler(RequestInfo requestInfo, RequestHandler handler) throws DuplicateRequestHandlerException;

    RequestHandler getHandler(String uri, HttpMethod httpMethod) throws CannotResolveHandlerException;
}
