package webserver.request;

import support.HttpMethod;
import webserver.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.StringUtils.NEW_LINE;

public class HttpRequest {

    private final HttpMethod method;
    private final String path;
    private final Query query = new Query();
    private final Header header = new Header();
    private final String version;
    private final String body;

    public static class RequestHeaderBuilder {
        private String requestLine;
        private final List<String> headers = new ArrayList<>();
        private String body;

        public RequestHeaderBuilder requestLine(String requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public RequestHeaderBuilder header(String header) {
            this.headers.add(header);
            return this;
        }

        public RequestHeaderBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, headers, body);
        }

    }

    private HttpRequest(String requestLine, List<String> headers, String body) {
        // 메소드 분리
        String[] tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);

        // url 파싱
        String[] pathAndQuery = tokens[1].split("\\?");
        this.path = pathAndQuery[0];
        if (pathAndQuery.length > 1) {
            String[] queries = pathAndQuery[1].split("&");
            Arrays.stream(queries)
                    .forEach(queryComponent -> {
                        String[] keyAndValue = queryComponent.split("=");
                        this.query.appendQuery(keyAndValue[0], keyAndValue[1]);
                    });
        }

        this.version = tokens[2];

        // 헤더 분리
        headers.forEach(
                header -> {
                    String[] split = header.split(":");
                    this.header.appendHeader(split[0], split[1].trim());
                }
        );

        // body
        this.body = body;

    }

    public String getHeaderValue(String key) {
        return header.getValue(key);
    }

    public HttpMethod getRequestMethod() {
        return method;
    }

    public String getRequestPath() {
        return path;
    }

    public Query getRequestQuery() {
        return query;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(method)
                .append(" ")
                .append(path)
                .append(query)
                .append(" ")
                .append(version)
                .append(NEW_LINE)
                .append(header.buildHeader())
                .append(body)
                .toString();
    }

}
