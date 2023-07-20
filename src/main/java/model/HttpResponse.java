package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ServletContainer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);

    private String body;

    private Map<String, String> headers;

    private final String uri;
    private final HttpRequest request;

    private String version;

    private int statusCode;

    public HttpResponse(HttpRequest request) {
        this.request = request;

        uri = request.getRequestURI();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
    public String body() {
        return body;
    }

    public int getStatus() {
        return statusCode;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    //TODO: sendRedirect 구현

    //TODO: sendError 구현

    //TODO: response를 byte[]로 출력
    public byte[] write() {
        return "response".getBytes();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
