package webserver.http.response;

public enum StatusLine {


    HTTP_VERSION("HTTP/1.1 "),
    RESPONSE_200("200"),
    RESPONSE_302("302"),
    RESPONSE_404("404");

    private final String literal;

    StatusLine(String literal) {
        this.literal = literal;
    }

    public String getConstant() {
        return literal;
    }
}
