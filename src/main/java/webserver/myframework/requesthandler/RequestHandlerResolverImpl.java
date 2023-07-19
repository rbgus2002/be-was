package webserver.myframework.requesthandler;

import webserver.http.HttpMethod;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.requesthandler.exception.CannotResolveHandlerException;
import webserver.myframework.requesthandler.exception.DuplicateRequestHandlerException;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestHandlerResolverImpl implements RequestHandlerResolver {
    private final Map<RequestInfo, RequestHandler> handlerMap = new HashMap<>();

    @Override
    public void registerHandler(RequestInfo requestInfo, RequestHandler handler) throws DuplicateRequestHandlerException {
        RequestHandler requestHandler = handlerMap.get(requestInfo);
        if(requestHandler != null) {
            throw new DuplicateRequestHandlerException();
        }
        handlerMap.put(requestInfo, handler);
    }

    @Override
    public RequestHandler resolveHandler(String uri, HttpMethod httpMethod) throws CannotResolveHandlerException {
        RequestInfo matchedRequestInfo = handlerMap.keySet().stream()
                .filter(info -> info.isUri(uri) && info.isHttpMethod(httpMethod))
                .findFirst()
                .orElseThrow(CannotResolveHandlerException::new);


        return handlerMap.get(matchedRequestInfo);
    }
}
