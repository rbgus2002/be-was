package webserver.response;

import utils.StringUtils;
import webserver.Header;

public class HttpResponse {

    private final static String HTTP_VERSION = "HTTP/1.1";
    private HttpStatus status;
    private final Header header = new Header();
    private byte[] body;

    public String buildResponseHeader() {
        StringBuilder stringBuilder = new StringBuilder();

        // Build Response-Line
        stringBuilder.append(HTTP_VERSION)
                .append(" ")
                .append(status.getMessage())
                .append(StringUtils.CRLF);

        // Build Response-Header
        stringBuilder.append(header.buildHeader())
                .append(StringUtils.CRLF);

        return stringBuilder.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void appendHeader(String key, String value) {
        header.appendHeader(key, value);
    }

    public void appendHeader(Header header) {
        this.header.appendHeader(header);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
