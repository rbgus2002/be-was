package webserver.request;

import support.web.HttpMethod;
import utils.StringUtils;
import webserver.Header;

import java.util.ArrayList;
import java.util.List;

import static utils.StringUtils.CRLF;

public class HttpRequest {

    private final HttpMethod method;
    private final String path;
    private QueryParameter query;
    private final String version;
    private final Header header = new Header();
    private QueryParameter body;

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
        // 메소드 분리
        String[] tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);

        // url 파싱
        String[] pathAndQuery = tokens[1].split("\\?");
        this.path = pathAndQuery[0];
        if (pathAndQuery.length > 1) {
            query = new QueryParameter(pathAndQuery[1]);
        }


        this.version = tokens[2];

        // 헤더 분리
        headers.forEach(
                header -> {
                    String[] split = header.split(":");
                    this.header.appendHeader(split[0], split[1].trim());
                }
        );

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

    public QueryParameter getQuery() {
        if (query == null) {
            query = new QueryParameter();
        }
        return query;
    }

    public QueryParameter getBody() {
        if (body == null) {
            body = new QueryParameter();
        }
        return body;
    }

    public void setBody(String body) {
        this.body = new QueryParameter(body);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(method)
                .append(" ")
                .append(path)
                .append(getQuery().size() != 0 ? "?" + getQuery() : "")
                .append(" ")
                .append(version)
                .append(CRLF)
                .append(header.buildHeader())
                .append(getBody().size() != 0 ? StringUtils.CRLF + "Body : " + getBody() : "")
                .toString();
    }

}
