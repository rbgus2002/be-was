package webserver.request;

import support.web.HttpMethod;
import webserver.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.StringUtils.NEW_LINE;

public class HttpRequest {

    private final HttpMethod method;
    private final String path;
    private final Query query;
    private final Header header = new Header();
    private final String version;
    private Parameter body;

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
        query = pathAndQuery.length > 1 ? new Query(pathAndQuery[1]) : null;


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

    public Optional<KeyValue> getPathQueryOrParameter() {
        if (body == null && query == null) {
            return Optional.of(new Parameter());
        }
        return body != null ? Optional.of(body) : Optional.of(query);
    }

    public void setBody(String body) {
        this.body = new Parameter(body);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(method)
                .append(" ")
                .append(path)
                .append(query != null ? query : "")
                .append(" ")
                .append(version)
                .append(NEW_LINE)
                .append(header.buildHeader())
                .append(body != null ? body : "")
                .toString();
    }

}
