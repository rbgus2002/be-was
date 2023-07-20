package webserver.http;

import java.io.DataOutputStream;

public class HttpResponse {
    private final String status;
    private final byte[] body;

    public HttpResponse(String status, byte[] body) {
        this.status = status;
        this.body = body;
    }
}
