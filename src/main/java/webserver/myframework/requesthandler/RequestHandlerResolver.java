package webserver.myframework.requesthandler;

import webserver.http.HttpMethod;
import webserver.myframework.requesthandler.exception.NotMatchedMethodException;
import webserver.myframework.requesthandler.exception.NotMatchedUriException;
import webserver.myframework.requesthandler.exception.DuplicateRequestHandlerException;

public interface RequestHandlerResolver {
    void registerHandler(RequestInfo requestInfo, RequestHandler handler) throws DuplicateRequestHandlerException;

    RequestHandler resolveHandler(String uri, HttpMethod httpMethod) throws NotMatchedUriException, NotMatchedMethodException;
}
