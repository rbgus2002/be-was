package webserver.request;

import support.HttpMethod;
import webserver.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final Header header = new Header();

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

        public HttpRequest build() {
            return new HttpRequest(requestLine, headers);
        }
    }

    private HttpRequest(String requestLine, List<String> headers) {
        String[] tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);

        this.url = tokens[1];
        headers.forEach(
                s -> {
                    String[] split = s.split(":");
                    header.appendHeader(split[0], split[1].trim());
                }
        );
    }

    public String getHeaders() {
        return header.buildHeader();
    }

    public HttpMethod getRequestMethod() {
        return method;
    }

    public String getRequestUrl() {
        return url;
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
