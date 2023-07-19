package webserver.myframework.requesthandler;

import webserver.http.HttpMethod;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.requesthandler.exception.NotMatchedMethodException;
import webserver.myframework.requesthandler.exception.NotMatchedUriException;
import webserver.myframework.requesthandler.exception.DuplicateRequestHandlerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public RequestHandler resolveHandler(String uri, HttpMethod httpMethod) throws NotMatchedUriException, NotMatchedMethodException {
        List<RequestInfo> matchedUriInfo = handlerMap.keySet().stream()
                .filter(info -> info.isUri(uri))
                .collect(Collectors.toList());

        if(matchedUriInfo.isEmpty()) {
            throw new NotMatchedUriException();
        }

        RequestInfo completeMatchedInfo = matchedUriInfo.stream()
                .filter(info -> info.isHttpMethod(httpMethod))
                .findFirst()
                .orElseThrow(NotMatchedMethodException::new);

        return handlerMap.get(completeMatchedInfo);
    }
}
