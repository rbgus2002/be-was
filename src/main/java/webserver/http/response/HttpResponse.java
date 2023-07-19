package webserver.http.response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.stream.Collectors;
import webserver.http.Headers;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;

public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String TEMPLATES = "/templates/";

    private final ResponseLine responseLine;
    private final Headers headers;
    private final byte[] body;

    private HttpResponse(final ResponseLine responseLine, final Headers headers, final byte[] body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse from(final HttpRequest httpRequest) {
        try {
            byte[] resource = findResource(httpRequest);
            if (resource == null) {
                return new HttpResponse(new ResponseLine(StatusCode.NOT_FOUND), Headers.create(), null);
            }
            return new HttpResponse(new ResponseLine(StatusCode.OK), Headers.create(resource.length), resource);
        } catch (IOException e) {
            return new HttpResponse(new ResponseLine(StatusCode.INTERNAL_ERROR), Headers.create(), null);
        }
    }

    private static byte[] findResource(final HttpRequest request) throws IOException {
        if (request.getRequestLine() == null) {
            return null;
        }
        URL resource = HttpResponse.class.getResource(TEMPLATES + request.getRequestLine().getTarget().toString());
        return write(resource);
    }

    private static byte[] write(final URL resource) throws IOException {
        if (resource == null) {
            return null;
        }
        String file = resource.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .collect(Collectors.joining())
                    .getBytes();
        }
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
