package common.http;

import common.enums.ContentType;
import common.enums.RequestMethod;
import common.wrapper.Cookies;
import common.wrapper.Headers;
import common.wrapper.Queries;

import static utils.StringUtils.NEW_LINE;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, Headers headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public String getRequestPath() {
        return requestLine.getRequestPath();
    }

    public ContentType getRequestContentType() {
        return requestLine.getRequestContentType();
    }

    public Queries getQueries() {
        RequestMethod requestMethod = getRequestMethod();

        if (requestMethod.equals(RequestMethod.GET)) {
            return requestLine.getQueries().orElseGet(Queries::new);
        }

        if (requestMethod.equals(RequestMethod.POST)) {
            return requestBody.getQueries().orElseGet(Queries::new);
        }

        return new Queries();
    }

    public Headers getHeaders() {
        return headers;
    }

    public Cookies getCookies() {
        return headers.getCookies();
    }

    public String getBody() {
        return requestBody.getSource();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE)
                .append("[Request Method] ").append(getRequestMethod()).append(NEW_LINE)
                .append("[Request path] ").append(requestLine.getRequestPath()).append(NEW_LINE);

        for (String headerKey : headers.getKeys()) {
            sb.append("[Headers] ").append(headerKey).append(": ").append(headers.getValue(headerKey)).append(NEW_LINE);
        }

        Queries queries = getQueries();
        for (String queryKey : queries.getKeys()) {
            sb.append("[Queries] ").append(queryKey).append(": ").append(queries.getValue(queryKey)).append(NEW_LINE);
        }

        sb.append("[Body] ").append(requestBody.getSource()).append(NEW_LINE);

        return sb.toString();
    }

}
