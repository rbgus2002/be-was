package model;

import model.enums.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

import static util.StringUtils.NEXTLINE;
import static util.StringUtils.SPACE;

public class HttpResponse {
    private final String protocol;
    private final HttpStatusCode statusCode;
    private final HttpHeader httpHeader;
    private final String body;

    // TODO 빌더 패턴 적용하기
    private HttpResponse(String protocol, HttpStatusCode statusCode, HttpHeader header, String body) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.httpHeader = header;
        this.body = body;
    }

    public static HttpResponse of(HttpRequest httpRequest, HttpStatusCode statusCode, String body) {
        Map<String, String> header = new HashMap<>();
        int lengthOfBody = body.getBytes().length;

        // TODO 수정하기
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", String.valueOf(lengthOfBody));
        HttpHeader httpHeader = HttpHeader.of(header);

        return new HttpResponse(
                httpRequest.getProtocol(),
                statusCode,
                httpHeader,
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
        return body.getBytes();
    }
}

