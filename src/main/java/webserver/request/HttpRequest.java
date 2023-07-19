package webserver.request;

import webserver.Header;

public class HttpRequest {

    private final HttpMethod httpMethod;
    private final String version;
    private final String path;
    private final Header header;

    public HttpRequest(String request, String header) {

        String[] tokens = request.split(" ");

        this.httpMethod = HttpMethod.valueOf(tokens[0]);
        this.path = tokens[1];
        this.version = tokens[2];
        this.header = Header.of(header);
    }
}
