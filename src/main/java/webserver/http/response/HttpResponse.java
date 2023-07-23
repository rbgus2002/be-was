package webserver.http.response;

import webserver.http.Headers;
import webserver.http.HttpStatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static utils.StringUtils.CRLF;
import static utils.StringUtils.appendNewLine;

public class HttpResponse {
    private String version;
    private HttpStatusCode statusCode;
    private Headers headers;
    private Body body;

    private HttpResponse(String version, HttpStatusCode statusCode, Body body, Headers headers) {
        this.version = version;
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static HttpResponse of(String version, HttpStatusCode statusCode, Body body) {
        return new HttpResponse(version, statusCode, body, Headers.from(body));
    }

    public static HttpResponse of(String version, HttpStatusCode statusCode) {
        return new HttpResponse(version, statusCode, Body.emptyBody(), Headers.from(Body.emptyBody()));
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBytes(appendNewLine(version + " " + statusCode.toString()));
        dos.writeBytes(headers.toString());
        dos.writeBytes(CRLF);
        dos.write(body, 0, body.length);
    }
}
