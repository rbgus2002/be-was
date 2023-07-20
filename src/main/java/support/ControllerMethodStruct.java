package support;

import java.lang.reflect.Method;

public class ControllerMethodStruct {

    private final HttpMethod httpMethod;
    private final Method method;

    public ControllerMethodStruct(HttpMethod httpMethod, Method method) {
        this.httpMethod = httpMethod;
        this.method = method;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Method getMethod() {
        return method;
    }

}
