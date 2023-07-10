package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

public class CustomHttpRequest extends HttpRequest {

    private final HttpMethod method;
    private final URI uri;
    private final Optional<HttpClient.Version> version;
    private final HttpHeaders httpHeaders;

    public CustomHttpRequest(BufferedReader reader) throws URISyntaxException, IOException {
        String[] split = reader.readLine().split(" ");
        this.method = HttpMethod.of(split[0]);
        this.uri = new URI(split[1]);
        this.version = getHttpVersion(split[2]);
        this.httpHeaders = null;
    }

    private Optional<HttpClient.Version> getHttpVersion(String version) {
        if (version.equals("HTTP/1.1")) {
            return Optional.of(HttpClient.Version.HTTP_1_1);
        } else if (version.equals("HTTP/2.0")) {
            return Optional.of(HttpClient.Version.HTTP_2);
        }
        return Optional.empty();
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
        return this.version;
    }

    @Override
    public HttpHeaders headers() {
        return this.httpHeaders;
    }
}
