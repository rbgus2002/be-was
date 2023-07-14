package webserver.model;

import java.util.Map;

public class Request {
    public enum Method {
        GET, POST, PUT, DELETE
    };

    private final Method method;
    private final String version;
    private final String targetUri;
    private final Map<String, String> queryParameterMap;
    private final Map<String, String> headerMap;
    private final String body;

    public Request(Method method, String version, String targetUri,
                   Map<String, String> queryParameterMap, Map<String, String> headerMap, String body) {
        this.method = method;
        this.version = version;
        this.targetUri = targetUri;
        this.queryParameterMap = queryParameterMap;
        this.headerMap = headerMap;
        this.body = body;
    }

    public Method getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getBody() {
        return body;
    }

}
