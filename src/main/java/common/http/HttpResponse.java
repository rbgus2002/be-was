package common.http;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.wrapper.Headers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static webserver.ServerConfig.STATIC_PATH;

public class HttpResponse {

    private ResponseCode responseLine;
    private Headers headers;
    private byte[] body;

    public HttpResponse() {
        headers = new Headers();
    }

    public String toStringResponseLine() {
        return responseLine.getDescription() + "\r\n";
    }

    public String toStringHeaders() {
        StringBuilder headerBuilder = new StringBuilder();

        String headerLine;
        for (String key : headers.getKeys()) {
            headerLine = key + ": " + headers.getValue(key) + "\r\n";
            headerBuilder.append(headerLine);
        }

        for (Cookie cookie : headers.getSetCookies()) {
            headerLine = "Set-Cookie: " + cookie.toString() + "\r\n";
            headerBuilder.append(headerLine);
        }

        return headerBuilder.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public void setResponseLine(ResponseCode responseCode) {
        this.responseLine = responseCode;
    }

    public void addHeader(String key, String value) {
        headers.addAttribute(key, value);
    }

    public void addCookie(Cookie cookie) {
        headers.addSetCookies(cookie);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStaticContentResponse(ContentType type, String path) {
        ResponseCode responseLine = ResponseCode.OK;
        Headers headers = new Headers();
        byte[] body = new byte[0];

        try {
            body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());

            headers.addAttribute("Content-Type", type.getDescription());
            headers.addAttribute("Content-Length", String.valueOf(body.length));

        } catch (IOException e) {
            responseLine = ResponseCode.NOT_FOUND;

        } finally {
            this.responseLine = responseLine;
            this.headers = headers;
            this.body = body;
        }
    }

}
