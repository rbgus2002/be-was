package model;

import com.google.common.net.HttpHeaders;
import model.enums.HttpStatusCode;

import static util.StringUtils.NEXTLINE;
import static util.StringUtils.SPACE;

public class HttpResponse {
    private final String protocol;
    private final HttpStatusCode statusCode;
    private final HttpHeader httpHeader;
    private final byte[] body;

    private HttpResponse(HttpStatusCode statusCode, HttpHeader header, byte[] body) {
        this.protocol = "HTTP/1.1";
        this.statusCode = statusCode;
        this.httpHeader = header;
        this.body = body;
    }

    public static HttpResponse of(HttpStatusCode statusCode, HttpHeader header, byte[] body) {
        return new HttpResponse(
                statusCode,
                header,
                body
        );
    }

    public String getHttpHeaderFormat() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(this.protocol)
                .append(SPACE)
                .append(this.statusCode.getValue())
                .append(SPACE)
                .append(this.statusCode.getDescription())
                .append(NEXTLINE)
                .append(httpHeader.stringify())
                .append(NEXTLINE)
                .toString();
    }

    public byte[] getByteArrayOfBody() {
        return body;
    }

    public void setCookie(String sessionId) {
        httpHeader.put(HttpHeaders.SET_COOKIE, "sid=" +sessionId + "; path=/");
    }
}

