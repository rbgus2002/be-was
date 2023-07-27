package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.enums.ContentType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.http.enums.ContentType.getContentTypeOfFile;
import static webserver.utils.StringUtils.NEW_LINE;

public class HttpResponseRenderer {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseRenderer.class);
    private static final HttpResponseRenderer HTTP_RESPONSE_RENDERER = new HttpResponseRenderer();

    public static HttpResponseRenderer getInstance() {
        return HTTP_RESPONSE_RENDERER;
    }

    public void responseRender(DataOutputStream dos, HttpResponse response) {
        try {
            // todo: static file controller에서 파일 있는지 여부만 확인해주기
            responseStatusLine(dos, response);
            responseHeader(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseStatusLine(DataOutputStream dos, HttpResponse response) throws IOException {
        String version = response.version();
        int statusCode = response.status().getStatusCode();
        String statusText = response.status().getStatusText();

        String statusLine = String.format("%s %d %s %s", version, statusCode, statusText, NEW_LINE);

        dos.writeBytes(statusLine);
//        logger.debug(statusLine);
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) throws IOException {
        if (response.sessionId() != null) {
            String cookieHeader = String.format("Set-Cookie: sid=%s; Path=/ %s", response.sessionId(), NEW_LINE);
            dos.writeBytes(cookieHeader);
//            logger.debug(cookieHeader);
        }

        if (response.redirect() != null) {
            String locationHeader = String.format("Location: %s %s", response.redirect(), NEW_LINE);
            dos.writeBytes(locationHeader);
//            logger.debug(locationHeader);
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse response) throws IOException {
        ContentType contentType = getContentTypeOfFile(response.fileName());
        int contentLength = 0;
        byte[] body = new byte[0];

        if (!"".equals(response.fileName())) {
            body = Files.readAllBytes(Paths.get(response.fileName()));
            // todo: 동적 HTML 수정
            contentLength = body.length;
        }

        String contentTypeHeader = String.format("Content-Type: %s %s", contentType.getMIMEString(), NEW_LINE);
        String contentLengthHeader = String.format("Content-Length: %d %s", contentLength, NEW_LINE);

        dos.writeBytes(contentTypeHeader);
//        logger.debug(contentTypeHeader);
        dos.writeBytes(contentLengthHeader);
        dos.writeBytes(NEW_LINE);
//        logger.debug(contentLengthHeader);

        dos.write(body, 0, body.length);
    }


}
