package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.enums.ContentType;

import java.io.DataOutputStream;
import java.io.IOException;

import static webserver.utils.StringUtils.NEW_LINE;

public class HttpResponseRenderer {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseRenderer.class);
    private static final HttpResponseRenderer HTTP_RESPONSE_RENDERER = new HttpResponseRenderer();

    public static HttpResponseRenderer getInstance() {
        return HTTP_RESPONSE_RENDERER;
    }

    public void responseRender(DataOutputStream dos, HttpResponse response) {
        logger.debug("Render response");
        responseHeader(dos, response);
        responseBody(dos, response);
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(String.format("%s %d %s %s", response.version(), response.statusCode(), response.statusText(), NEW_LINE));
            logger.debug("{} {} {}", response.version(), response.statusCode(), response.statusText());

            for(String header: response.headers()) {
                dos.writeBytes(String.format("%s: %s%s", header, response.getHeader(header), NEW_LINE));
                logger.debug("{}: {}", header, response.getHeader(header));
            }

            String contentType = String.format("Content-Type: %s", response.contentType().getTypeString());
            if(response.contentType() == ContentType.HTML) contentType = contentType.concat(";charset=utf-8");
            dos.writeBytes(contentType.concat(NEW_LINE));
//            logger.debug("Content-Type: {} ", response.contentType());

            dos.writeBytes("Content-Length: " + response.body().length + NEW_LINE);
//            logger.debug("response headers: {}", response.headers().toString());

            dos.writeBytes(NEW_LINE);

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
