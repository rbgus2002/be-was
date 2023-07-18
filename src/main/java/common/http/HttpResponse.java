package common.http;

import java.util.Map;
import java.util.Map.Entry;

public class HttpResponse {
    private ResponseLine responseLine;
    private Map<String, String> headers;
    private byte[] body;

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

    public void setResponseLine(ResponseLine responseLine) {
        this.responseLine = responseLine;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}