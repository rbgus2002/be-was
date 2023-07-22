package common.http;

import common.enums.ContentType;
import common.enums.RequestMethod;
import common.wrapper.Queries;

import java.util.Optional;

public class RequestLine {

    private final RequestMethod requestMethod;
    private final Uri uri;

    public RequestLine(RequestMethod requestMethod, Uri uri) {
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return uri.getRequestPath();
    }

    public Optional<Queries> getQueries() {
        return Optional.ofNullable(uri.getQueries());
    }

    public ContentType getRequestContentType() {
        return uri.getContentType();
    }

}
