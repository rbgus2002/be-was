package common;

import java.util.Map;
import java.util.Map.Entry;

public class HttpResponse {
    private final ResponseLine responseLine;
    private final Map<String, String> headers;
    private final byte[] body;

    public HttpResponse(ResponseLine responseLine, Map<String, String> headers, byte[] body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public String getResponseLine() {
        return responseLine.toString() + "\r\n";
    }

    public String getHeaders() {
        StringBuilder headerBuilder = new StringBuilder();

        String headerLine;
        for (Entry<String, String> header : headers.entrySet()) {
            headerLine = header.getKey() + ": " + header.getValue() + "\r\n";
            headerBuilder.append(headerLine);
        }
        return headerBuilder.toString();
    }

    public byte[] getBody() {
        return body;
    }
}