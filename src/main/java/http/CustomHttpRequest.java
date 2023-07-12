package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static util.Utils.getHttpVersion;

public class CustomHttpRequest extends HttpRequest {

    private final HttpMethod method;
    private final URI uri;
    private final HttpClient.Version version;

    public CustomHttpRequest(List<String> strings) throws URISyntaxException, IOException {
        String[] split = strings.get(0).split(" ");
        this.method = HttpMethod.of(split[0]);
        this.uri = new URI(split[1]);
        this.version = getHttpVersion(split[2]).orElse(null);
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
