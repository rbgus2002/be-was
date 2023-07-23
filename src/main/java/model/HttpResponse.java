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

    private HttpResponse(String protocol, HttpStatusCode statusCode, HttpHeader header, byte[] body) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.httpHeader = header;
        this.body = body;
    }

    public static HttpResponse of(HttpRequest httpRequest, HttpStatusCode statusCode, HttpHeader header, byte[] body) {
        return new HttpResponse(
                httpRequest.getProtocol(),
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
                .append(httpHeader.stringfy())
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

