package http;

import util.HttpUtils;
import util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
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
        this.headers = parseHeader(reader);
        this.method = HttpUtils.Method.of(requestParts[0]);
        this.uri = constructUri(requestParts[1], headers);
        this.version = HttpUtils.getHttpVersion(requestParts[2]).orElse(null);
        this.mime = decideMime(this.uri.getPath());
        this.body = parseBody(reader);
    }

    private Map<String, String> parseHeader(BufferedReader reader) throws IOException {
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                map.put(headerName, headerValue);
            }
        }
        return map;
    }

    private String parseBody(BufferedReader reader) throws IOException {
        int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
        if (this.method.equals(HttpUtils.Method.GET) || contentLength == 0) {
            return null;
        }
        char[] buffer = new char[contentLength];
        reader.read(buffer);
        return URLDecoder.decode(new String(buffer), StandardCharsets.UTF_8);
    }

    private URI constructUri(String file, Map<String, String> headers) throws URISyntaxException {
        String host = headers.get("Host");
        return new URI("http://" + host + URLDecoder.decode(file, StandardCharsets.UTF_8));
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

    private Mime decideMime(String path) {
        String extension = StringUtils.getExtension(path);

        return Arrays.stream(Mime.values())
                .filter(mime -> mime.getExtension().equals(extension))
                .findFirst()
                .orElse(Mime.DEFAULT);
    }
}
