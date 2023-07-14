package http;

import java.util.Map;

import static http.HttpRequestParser.*;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> params;
    private String httpVersion;

    public HttpRequest(String requestLine){
        this.method = parseMethodFromRequestLine(requestLine);
        this.path = parsePathFromRequestLine(requestLine);
        this.params = parseParamsFromRequestLine(requestLine);
        this.httpVersion = parseVersionFromRequestLine(requestLine);
    }

    public String getMethod(){
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return httpVersion;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
