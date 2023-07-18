package webserver.http.response;

import static webserver.http.response.StatusLine.HTTP_VERSION;

public enum ResponseHeader {

    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");


    private final String literal;

    ResponseHeader(String literal) {
        this.literal = literal;
    }

    public String getConstant() {
        return literal;
    }

    public static String getStatusHeader(StatusLine statusLine) {
        return HTTP_VERSION.getConstant() + statusLine.getConstant();
    }
}
