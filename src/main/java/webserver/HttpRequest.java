package webserver;

import util.HttpRequestUtils;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    private final String method;
    private final String requestPath;
    private final String version;

    public HttpRequest(InputStream in) throws IOException {
        String firstLine = HttpRequestUtils.getFirstLine(in);
        this.method = HttpRequestUtils.getMethod(firstLine);
        this.requestPath = HttpRequestUtils.getUrl(firstLine);
        this.version = HttpRequestUtils.getVersion(firstLine);
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
}
