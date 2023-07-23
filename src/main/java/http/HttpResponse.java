package http;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private final String path;
    private final HttpStatus httpStatus;
    private final Mime contentType;
    private final List<Cookie> cookies;
    private byte[] body;

    public static HttpResponse ok(String path, Mime mime) {
        return new HttpResponse(path, mime, HttpStatus.OK);
    }

    public static HttpResponse redirect(String path) {
        return new HttpResponse(path, null, HttpStatus.FOUND);
    }

    public static HttpResponse notFound(String path, Mime mime) {
        return new HttpResponse(path, mime, HttpStatus.NOT_FOUND);
    }

    private HttpResponse(String path, Mime mime, HttpStatus httpStatus) {
        this.path = path;
        this.contentType = mime;
        this.httpStatus = httpStatus;
        this.cookies = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Mime getContentType() {
        return contentType;
    }

    public void setCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return new ArrayList<>(this.cookies);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return this.body;
    }
}
