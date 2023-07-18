package http;

import util.FileUtils;
import util.HttpUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private final HttpUtils.Method method;
    private final URI uri;
    private final HttpClient.Version version;
    private final Mime mime;
    private final Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");

    public HttpRequest(List<String> requestLines) throws URISyntaxException {
        String[] requestParts = requestLines.get(0).split(" ");
        Map<String, String> headers = parseHeaders(requestLines);
        this.method = HttpUtils.Method.of(requestParts[0]);
        this.uri = constructUri(requestParts[1], headers);
        this.version = HttpUtils.getHttpVersion(requestParts[2]).orElse(null);
        this.mime = decideMime(this.uri.getPath());
    }

    public Map<String, String> parameters() {
        if (this.uri.getQuery() == null) {
            return Collections.emptyMap();
        }
        Matcher matcher = pat.matcher(this.uri.getQuery());
        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    private Map<String, String> parseHeaders(List<String> requestLines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = 1; i < requestLines.size(); i++) {
            String line = requestLines.get(i);
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                headers.put(headerName, headerValue);
            }
        }
        return headers;
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

    private Mime decideMime(String path) {
        String extension = FileUtils.getExtension(path);

        return Arrays.stream(Mime.values())
                .filter(mime -> mime.getExtension().equals(extension))
                .findFirst()
                .orElse(Mime.DEFAULT);
    }
}
