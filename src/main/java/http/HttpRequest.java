package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.Utils.getHttpVersion;

public class HttpRequest {

    private final HttpMethod method;
    private final URI uri;
    private final HttpClient.Version version;
    private final Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");

    public HttpRequest(List<String> requestLines) throws URISyntaxException, IOException {
        String[] requestParts = requestLines.get(0).split(" ");
        Map<String, String> headers = parseHeaders(requestLines);
        this.method = HttpMethod.of(requestParts[0]);
        this.uri = constructUri(requestParts[1], headers);
        this.version = getHttpVersion(requestParts[2]).orElse(null);
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

    public HttpMethod method() {
        return this.method;
    }

    public URI uri() {
        return this.uri;
    }

    public HttpClient.Version version() {
        return this.version;
    }
}
