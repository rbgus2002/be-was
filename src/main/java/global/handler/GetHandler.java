package global.handler;

import annotations.GetMapping;
import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.request.RequestLine;
import global.util.SessionUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GetHandler implements Handler {
    private final HttpMethod httpMethod = HttpMethod.GET;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final SessionUtil sessionUtil;

    public GetHandler(RequestHeader requestHeader, RequestBody requestBody, SessionUtil sessionUtil) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.sessionUtil = sessionUtil;
    }

    public boolean matchHttpMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public byte[] startController(RequestLine requestLine, Controller controller) throws Exception {
        for (Method method : Controller.class.getDeclaredMethods()) {
            if (isGetMapping(method, requestLine.getUri())) {
                return (byte[]) method.invoke(controller, requestHeader, requestBody, sessionUtil);
            }
        }

        Set<String> GetMappingsSet = getDeclaredGetMappings();
        if (GetMappingsSet.contains(requestLine.getUri())) {
            return requestLine.getUri().getBytes();
        }
        throw new BadRequestException();
    }

    private Set<String> getDeclaredGetMappings() {
        return Arrays.stream(Controller.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GetMapping.class))
                .map(method -> method.getAnnotation(GetMapping.class).path())
                .collect(Collectors.toSet());
    }

    private boolean isGetMapping(Method method, String uri) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            String path = method.getAnnotation(GetMapping.class).path();
            return path.equals(uri);
        }
        return false;
    }
}