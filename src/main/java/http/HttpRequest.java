package http;

import util.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Map;

public class HttpRequest {

    private final HttpUtils.Method method;
    private final URI uri;
    private final HttpClient.Version version;
    private final Mime mime;
    private final Map<String, String> headers;
    private final String body;

    public HttpRequest(BufferedReader reader) throws URISyntaxException, IOException {
        String requestLine = reader.readLine();
        String[] requestParts = requestLine.split(" ");
        this.headers = HttpUtils.parseHeader(reader);
        this.method = HttpUtils.Method.of(requestParts[0]);
        this.uri = HttpUtils.constructUri(requestParts[1], headers);
        this.version = HttpUtils.getHttpVersion(requestParts[2]).orElse(null);
        this.mime = HttpUtils.decideMime(this.uri.getPath());
        this.body = HttpUtils.parseBody(reader, this.headers, this.method);
    }

    public HttpUtils.Method method() {
        return this.method;
    }

    public URI uri() {
        return this.uri;
    }

    public HttpClient.Version version() {
        return this.version;
    }

    public Mime mime() {
        return this.mime;
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getBody() {
        return this.body;
    }
}
