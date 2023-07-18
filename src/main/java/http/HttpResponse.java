package http;

public class HttpResponse {

    private final String path;
    private final HttpStatus httpStatus;
    private final Mime contentType;

    public static HttpResponse ok(String path, Mime mime) {
        return new HttpResponse(path, mime, HttpStatus.OK);
    }

    public static HttpResponse redirect(String path) {
        return new HttpResponse(path, null, HttpStatus.FOUND);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(null, null, HttpStatus.NOT_FOUND);
    }

    private HttpResponse(String path, Mime mime, HttpStatus httpStatus) {
        this.path = path;
        this.contentType = mime;
        this.httpStatus = httpStatus;
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
}
