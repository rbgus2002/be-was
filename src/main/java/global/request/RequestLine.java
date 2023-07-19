package global.request;

import exception.BadRequestException;
import global.constant.HttpMethod;

public class RequestLine {
    private static final int REQUEST_LINE_SIZE = 3;
    private static final int METHOD = 0;
    private static final int URI = 1;
    private static final String EMPTY_SPACE_SEPARATOR = " ";

    private final String httpMethod;
    private final String uri;

    public RequestLine(String requestLine) {
        String[] requests = splitRequestLine(requestLine);
        this.httpMethod = requests[METHOD];
        this.uri = requests[URI];
    }

    private String[] splitRequestLine(String requestLine) {
        String[] requests = requestLine.split(EMPTY_SPACE_SEPARATOR);
        if (requests.length < REQUEST_LINE_SIZE) {
            throw new BadRequestException();
        }
        return requests;
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.findHttpMethod(httpMethod);
    }

    public String getUri() {
        return this.uri;
    }
}