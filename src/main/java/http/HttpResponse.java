package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    private final String path;
    private final HttpStatus httpStatus;
    private final Mime contentType;
    private final List<Cookie> cookies;
    private byte[] body;
    private final Map<String, Object> viewParameters;

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
        this.viewParameters = new HashMap<>();
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

    public void setViewParameters(String key, Object value) {
        this.viewParameters.put(key, value);
    }

    public Object getViewParameter(String key) {
        return this.viewParameters.get(key);
    }
}
