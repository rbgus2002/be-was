package webserver.http;

import util.HttpRequestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String requestPath;
    private final String version;

    private Map<String, String> params = new HashMap<>();

    public HttpRequest(String method, String requestPath, String version) {
        this.method = method;
        this.requestPath = requestPath;
        this.version = version;
    }

    public HttpRequest(InputStream in) throws IOException {
        String firstLine = HttpRequestUtils.getFirstLine(in);
        this.method = HttpRequestUtils.getMethod(firstLine);
        this.requestPath = HttpRequestUtils.getUrl(firstLine);
        this.version = HttpRequestUtils.getVersion(firstLine);

        String queryString = requestPath.split("\\?")[1];
        this.params = HttpRequestUtils.parseQueryString(queryString);
    }

    public String getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
