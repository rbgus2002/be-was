package webserver;

import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponseStringifier {

    public static final String SPACE = " ";
    public static final String CRLF = "\r\n";

    public String stringify(HttpResponse httpResponse) {
        StringBuilder responseBuilder = new StringBuilder();
        appendHttpVersion(responseBuilder, httpResponse);
        appendHttpStatus(responseBuilder, httpResponse);
        appendMetaData(responseBuilder, httpResponse);
        appendCRLF(responseBuilder);
        if (httpResponse.hasBody()) {
            appendBody(responseBuilder, httpResponse);
        }
        return responseBuilder.toString();
    }

    private void appendBody(StringBuilder responseBuilder, HttpResponse httpResponse) {
        byte[] body = httpResponse.getBody();
        if (body != null) {
            responseBuilder.append(new String(body, StandardCharsets.UTF_8));
        }
    }

    private void appendCRLF(StringBuilder responseBuilder) {
        responseBuilder.append(CRLF);
    }

    private void appendMetaData(StringBuilder responseBuilder, HttpResponse httpResponse) {
        Map<String, List<String>> metaData = httpResponse.getMetaData();
        for (String key : metaData.keySet()) {
            String values = String.join(SPACE, metaData.get(key));
            responseBuilder.append(key).append(":").append(SPACE)
                    .append(values).append(CRLF);
        }
    }

    private void appendHttpStatus(StringBuilder responseBuilder, HttpResponse httpResponse) {
        HttpStatus httpStatus = httpResponse.getHttpStatus();
        responseBuilder.append(httpStatus.getStatusCode()).append(SPACE);
        responseBuilder.append(httpStatus.getReasonPhrase()).append(CRLF);
    }

    private void appendHttpVersion(StringBuilder responseBuilder, HttpResponse httpResponse) {
        HttpVersion httpVersion = httpResponse.getHttpVersion();
        responseBuilder.append(httpVersion.getVersion()).append(SPACE);
    }
}
