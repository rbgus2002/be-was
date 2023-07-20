package webserver.http.request;

import webserver.http.Http.MIME;

public class RequestTarget {
    private static final String EXTENSION_START_TEXT = ".";

    private final String value;

    public RequestTarget(final String value) {
        this.value = value;
    }

    public QueryParameter getQueryParameter() {
        return QueryParameter.from(value);
    }

    public MIME getMIME() {
        String result = this.value.split(QueryParameter.REGEX)[0];
        if(result.contains(EXTENSION_START_TEXT)) {
            return MIME.findBy(result.substring(result.lastIndexOf(EXTENSION_START_TEXT)));
        }
        return MIME.NONE;
    }

    public String getPath() {
        if (this.value.contains(QueryParameter.DELIMITER)) {
            return this.value.substring(0, this.value.indexOf(QueryParameter.DELIMITER));
        }
        return this.value;
    }
}
