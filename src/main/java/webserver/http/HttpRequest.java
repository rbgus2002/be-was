package webserver.http;

import util.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final String method;
    private final String requestPath;
    private final String version;
    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> params = new HashMap<>();

    public HttpRequest(String method, String requestPath, String version) {
        this.method = method;
        this.requestPath = requestPath;
        this.version = version;
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String firstLine = bufferedReader.readLine();
        this.method = HttpRequestUtils.getMethod(firstLine);
        this.requestPath = HttpRequestUtils.getUrl(firstLine);
        this.version = HttpRequestUtils.getVersion(firstLine);
        this.headers = getHeaders(bufferedReader);
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

    private Map<String, String> getHeaders(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line = bufferedReader.readLine();
        while (!line.equals("")) {
            line = bufferedReader.readLine();
            String key = line.split(": ")[0];
            String value = line.split(": ")[1];
            headers.put(key, value);
        }

        return headers;
    }
}
