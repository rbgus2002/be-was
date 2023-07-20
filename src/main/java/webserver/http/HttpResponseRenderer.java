package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseRenderer {
    private final static Logger logger = LoggerFactory.getLogger(HttpResponseRenderer.class);
    private final static HttpResponseRenderer HTTP_RESPONSE_RENDERER = new HttpResponseRenderer();

    public static HttpResponseRenderer getInstance() {
        return HTTP_RESPONSE_RENDERER;
    }

    public void responseRender(DataOutputStream dos, HttpResponse response) {
        responseHeader(dos, response);
        responseBody(dos, response);
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) {
        try {
            // TODO: \r\n appendNewLine으로 바꾸기?
            dos.writeBytes(String.format("%s %d %s \r\n", response.version(), response.statusCode(), response.statusText()));
            logger.debug("{} {} {}", response.version(), response.statusCode(), response.statusText());
            dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", response.contentType()));
//            logger.debug("Content-Type: {} ", response.contentType());
            dos.writeBytes("Content-Length: " + response.body().length + "\r\n");
//            logger.debug("response headers: {}", response.headers().toString());
            for(String header: response.headers().keySet()) {
                dos.writeBytes(String.format("%s: %s\r\n", header, response.headers().get(header)));
                logger.debug("{}: {}", header, response.headers().get(header));
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse response) {
        try {
            dos.write(response.body(), 0, response.body().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
