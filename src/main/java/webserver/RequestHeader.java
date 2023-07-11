package webserver;

import java.util.ArrayList;
import java.util.List;

public class RequestHeader {
    private String url;
    private final List<String> headers;

    public static class RequestHeaderBuilder {
        private String requestLine;
        private final List<String> headers = new ArrayList<>();

        public RequestHeaderBuilder requestLine(String requestLine){
            this.requestLine = requestLine;
            return this;
        }

        public RequestHeaderBuilder header(String header) {
            this.headers.add(header);
            return this;
        }

        public RequestHeader build() {
            return new RequestHeader(requestLine, headers);
        }
    }

    private RequestHeader(String requestLine, List<String> headers) {
        String[] tokens = requestLine.split(" ");
        this.url = tokens[1];

        this.headers = headers;
    }

    public String getRequestUrl() {
        return url;
    }


}
