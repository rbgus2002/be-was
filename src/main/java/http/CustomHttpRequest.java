package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static util.Utils.getHttpVersion;

public class CustomHttpRequest extends HttpRequest {

    private final HttpMethod method;
    private final URI uri;
    private final HttpClient.Version version;

    public CustomHttpRequest(List<String> requestLines) throws URISyntaxException, IOException {
        String[] requestParts = requestLines.get(0).split(" ");
        Map<String, String> headers = parseHeaders(requestLines);
        this.method = HttpMethod.of(requestParts[0]);
        this.uri = constructUri(requestParts[1], headers);
        this.version = getHttpVersion(requestParts[2]).orElse(null);
    }

    private Map<String, String> parseHeaders(List<String> requestLines) throws IOException {
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

    @Override
    public Optional<BodyPublisher> bodyPublisher() {
        return Optional.empty();
    }

    @Override
    public String method() {
        return this.method.name();
    }

    @Override
    public Optional<Duration> timeout() {
        return Optional.empty();
    }

    @Override
    public boolean expectContinue() {
        return false;
    }

    @Override
    public URI uri() {
        return this.uri;
    }

    @Override
    public Optional<HttpClient.Version> version() {
        return Optional.of(this.version);
    }

    @Override
    public HttpHeaders headers() {
        return null;
    }
}
