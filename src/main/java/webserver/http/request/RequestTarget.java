package webserver.http.request;

import webserver.http.Http.MIME;

public class RequestTarget {
    private static final String EXTENSION_START_TEXT = ".";

    private final String value;

    public RequestTarget(final String value) {
        this.value = value;
    }

    public MIME getMIME() {
        String result = this.value.substring(this.value.lastIndexOf(EXTENSION_START_TEXT));
        if (result.contains(QueryParameter.DELIMITER)) {
            result = result.substring(this.value.lastIndexOf(EXTENSION_START_TEXT), result.indexOf(QueryParameter.DELIMITER));
        }
        return MIME.findBy(result);
    }

    public String getPath() {
        if (this.value.contains(QueryParameter.DELIMITER)) {
            return this.value.substring(0, this.value.indexOf(QueryParameter.DELIMITER));
        }
        return this.value;
    }
}
