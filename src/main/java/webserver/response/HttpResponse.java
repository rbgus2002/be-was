package webserver.response;

import webserver.ContentType;

import static utils.StringUtils.*;

public class HttpResponse {

    private final ContentType contentType;
    private final int lengthOfBodyContent;

    public HttpResponse(ContentType contentType, int lengthOfBodyContent) {
        this.contentType = contentType;
        this.lengthOfBodyContent = lengthOfBodyContent;
    }

    @Override
    public String toString() {
        return "HTTP/1.1 " + contentType.getDescription() + NEWLINE +
                "Content-Type: " + contentType.getDescription() + NEWLINE +
                "Content-Length: " + lengthOfBodyContent + NEWLINE +
                NEWLINE;
    }
}
