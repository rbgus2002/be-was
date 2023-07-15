package http;

import java.util.Map;

import static http.HttpRequestParser.*;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> params;
    private String httpVersion;

    public HttpRequest(String requestLine){
        String[] tokens = requestLine.split(" ");
        this.method = tokens[0];
        this.path = parsePathFromUrl(tokens[1]);
        this.params = parseParamsFromUrl(tokens[1]);
        this.httpVersion = tokens[2];
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
