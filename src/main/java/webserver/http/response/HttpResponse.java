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
    private ResponseBody body;

    private HttpResponse(String version, HttpStatusCode statusCode, ResponseBody body, Headers headers) {
        this.version = version;
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static HttpResponse of(String version, HttpStatusCode statusCode, ResponseBody body) {
        return new HttpResponse(version, statusCode, body, Headers.from(body));
    }

    public static HttpResponse of(String version, HttpStatusCode statusCode) {
        return new HttpResponse(version, statusCode, ResponseBody.emptyBody(), Headers.from(ResponseBody.emptyBody()));
    }

    public static HttpResponse redirectResponse(String version, String path) {
        return new HttpResponse(version, HttpStatusCode.FOUND, ResponseBody.emptyBody(), Headers.redirectHeaders(path));
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(appendNewLine(version + " " + statusCode.toString()));
        headers.write(dos);
        dos.writeBytes(CRLF);
        body.write(dos);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appendNewLine(version + " " + statusCode.toString()));
        stringBuilder.append(headers.toString());
        stringBuilder.append(CRLF);
        stringBuilder.append(body.toString());
        return stringBuilder.toString();
    }
}
