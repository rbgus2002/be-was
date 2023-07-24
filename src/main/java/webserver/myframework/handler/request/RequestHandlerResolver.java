package webserver.myframework.handler.request;

import webserver.http.HttpMethod;
import webserver.myframework.handler.request.exception.DuplicateRequestHandlerException;
import webserver.myframework.handler.request.exception.NotMatchedMethodException;
import webserver.myframework.handler.request.exception.NotMatchedUriException;

public interface RequestHandlerResolver {
    void registerHandler(RequestInfo requestInfo, RequestHandler handler) throws DuplicateRequestHandlerException;

    RequestHandler resolveHandler(String uri, HttpMethod httpMethod) throws NotMatchedUriException, NotMatchedMethodException;
}
