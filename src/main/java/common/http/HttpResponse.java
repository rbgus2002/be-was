package common.http;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.wrapper.Headers;
import utils.FileUtils;

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

    public void setUpStaticContentResponse(ContentType type, String path) {
        byte[] body = FileUtils.readFile(STATIC_PATH + path);

        if (body != null) {
            setResponseLine(ResponseCode.OK);
            addHeader("Content-Type", type.getDescription());
            addHeader("Content-Length", String.valueOf(body.length));
            setBody(body);
        } else {
            setResponseLine(ResponseCode.NOT_FOUND);
        }
    }

    public void setUpDefaultResponse(ResponseCode responseCode, ContentType contentType, byte[] body) {
        if (body != null) {
            setResponseLine(responseCode);
            addHeader("Content-Type", contentType.getDescription());
            addHeader("Content-Length", String.valueOf(body.length));
            setBody(body);
        } else {
            setResponseLine(ResponseCode.BAD_REQUEST);
        }
    }

    public void setUpRedirectResponse(String redirectionPath) {
        setResponseLine(ResponseCode.FOUND);
        addHeader("Location", redirectionPath);
        setBody(new byte[0]);
    }

}
