package webserver.response;

import utils.StringUtils;
import webserver.Header;
import webserver.response.strategy.ResponseHeaderStrategy;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private final static String HTTP_VERSION = "HTTP/1.1";
    private HttpStatus status;
    private final Header header = new Header();
    private byte[] body;

    public void response(DataOutputStream dos) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        // Build Response-Line
        stringBuilder.append(HTTP_VERSION)
                .append(" ")
                .append(status.getMessage())
                .append(StringUtils.NEW_LINE);

        // Build Response-Header
        stringBuilder.append(header.buildHeader())
                .append(StringUtils.NEW_LINE);

        // build Response-Body
        dos.writeBytes(stringBuilder.toString());
        if (body != null)
            dos.write(body, 0, body.length);
        dos.flush();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void buildHeader(ResponseHeaderStrategy message) {
        message.buildHeader(header);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
