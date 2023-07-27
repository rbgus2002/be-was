package webserver.http.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    public enum Method {
        GET, POST, PUT, DELETE;

        public static Method getMethodByName(String name) {
            return Arrays.stream(Method.values())
                    .filter(method -> name.equalsIgnoreCase(method.toString()))
                    .collect(Collectors.toList())
                    .get(0);
        }
    }

    private final Method method;
    private final String version;
    private final String targetUri;
    private final String path;
    private final Map<String, String> queryParameterMap;
    private final Map<String, String> headerMap;
    private final String sid;
    private final String body;
    private final Map<String, String> bodyParameterMap;

    public Request(Method method, String version, String targetUri, String path,
                   Map<String, String> queryParameterMap, Map<String, String> headerMap,
                   String sid, String body, Map<String, String> bodyParameterMap) {
        this.method = method;
        this.version = version;
        this.targetUri = targetUri;
        this.path = path;
        this.queryParameterMap = queryParameterMap;
        this.headerMap = headerMap;
        this.sid = sid;
        this.body = body;
        this.bodyParameterMap = bodyParameterMap;
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

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }

    public String getQueryParameter(String key) {
        return queryParameterMap.get(key);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getSid() {
        return sid;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getBodyParameterMap() {
        return bodyParameterMap;
    }

    public String getBodyParameter(String key) {
        return bodyParameterMap.get(key);
    }

    // queryParamter 혹은 bodyParameter에 해당 key가 존재하면 value를 반환합니다.(그렇지 않다면 null 반환)
    public String getParameter(String key) {
        if(queryParameterMap.containsKey(key)) {
            return queryParameterMap.get(key);
        }
        if(bodyParameterMap.containsKey(key)) {
            return bodyParameterMap.get(key);
        }

        return null;
    }
}
