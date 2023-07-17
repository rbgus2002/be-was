package webserver;

import utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestHeader {

    private final String requestLine;
    private final List<String> headers;

    public static class RequestHeaderBuilder {
        private String requestLine;
        private final List<String> headers = new ArrayList<>();

        public RequestHeaderBuilder requestLine(String requestLine) {
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
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public String getHeaders() {
        return requestLine + headers.stream()
                .reduce("", StringUtils::appendNewLine);
    }

    public String getRequestUrl() {
        String[] tokens = requestLine.split(" ");
        return tokens[1];
    }

    public String getRequestPath() {
        String[] pathAndQuery = getRequestUrl().split("\\?");
        return pathAndQuery[0];
    }

    public Query getRequestQuery() {
        String[] pathAndQuery = getRequestUrl().split("\\?");
        String queryString = pathAndQuery[1];

        Query query = new Query();
        String[] queries = queryString.split("&");
        Arrays.stream(queries)
                .forEach(queryComponent -> {
                    String[] keyAndValue = queryComponent.split("=");
                    query.appendQuery(keyAndValue[0], keyAndValue[1]);
                });
        return query;
    }

}
