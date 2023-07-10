
package webserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestHeader {
    private String requestLine;
    private final List<String> headers = new ArrayList<>();
    public RequestHeader() {
    }

    public void addRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void appendHeader(String header) {
        this.headers.add(header);
    }

    public String parseRequestUrl() throws IOException {
        String[] tokens = requestLine.split(" ");
        return tokens[1];
    }
}