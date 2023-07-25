package global.request;

import exception.BadRequestException;
import global.constant.HttpMethod;

import java.util.*;

public class RequestLine {
    private static final int REQUEST_LINE_SIZE = 3;
    private static final int METHOD = 0;
    private static final String EMPTY_SPACE_SEPARATOR = " ";
    private static final String AND_PERCENT_SEPARATOR = "&";
    private static final String EQUAL_SEPARATOR = "=";

    private static final String QUERY_PARAMETER_SEPARATOR = "\\?";
    private final String httpMethod;
    private final String path;
    private final Map<String, String> queryParams;

    public RequestLine(String requestLine) {
        String[] requests = splitRequestLine(requestLine);
        this.httpMethod = requests[METHOD];
        this.path = splitURI(requests[1]);
        this.queryParams = getQueryParameter(requests[1]);
    }

    private Map<String, String> getQueryParameter(String uri) {
        final Map<String, String> params = new LinkedHashMap<>();
        if (!isQueryParameter(uri)) {
            return params;
        }
        String queries = splitQueryParams(uri);
        String[] queriesSplit = queries.split(AND_PERCENT_SEPARATOR);
        for (String query : queriesSplit) {
            String[] split = query.split(EQUAL_SEPARATOR);
            params.put(split[0].trim(), split[1].trim());
        }
        return params;
    }

    private String[] splitRequestLine(String requestLine) {
        String[] requests = requestLine.split(EMPTY_SPACE_SEPARATOR);
        if (requests.length < REQUEST_LINE_SIZE) {
            throw new BadRequestException();
        }
        return requests;
    }

    private String splitURI(String uri) {
        return uri.split(QUERY_PARAMETER_SEPARATOR)[0];
    }

    private String splitQueryParams(String uri) {
        return uri.split(QUERY_PARAMETER_SEPARATOR)[1];
    }

    private boolean isQueryParameter(String uri) {
        String[] result = uri.split(QUERY_PARAMETER_SEPARATOR);
        return result.length > 1;
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.findHttpMethod(httpMethod);
    }

    public String getUri() {
        return this.path;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }
}