package exception;

import webserver.http.HttpUtil.STATUS;
import webserver.http.model.Response;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpUtil.*;
import static webserver.http.HttpUtil.MIME.HTML;

public abstract class HTTPException extends RuntimeException {
    private final STATUS status;

    public HTTPException(STATUS status, String message) {
        super(message);
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }

    public Response generateResponse() {
        String errorMessage = this.getMessage();
        byte[] body = errorMessage.getBytes();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, HTML.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));
        return new Response(status, headerMap, body);
    }
}
