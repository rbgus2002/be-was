package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HeaderParser;
import webserver.util.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClientConnection {


    private final DataOutputStream dos;
    private final HttpResponse httpResponse;

    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    public ClientConnection(DataOutputStream dos, HttpResponse httpResponse) {
        this.dos = dos;
        this.httpResponse = httpResponse;
    }

    public void sendRedirect(String redirectUrl) {
        RequestMessageHeader requestMessageHeader = httpResponse.getHeader();
        try {
            dos.writeBytes(requestMessageHeader.response302Header(redirectUrl));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("HttpResponse redirect end");
    }

    public void forward(String url) {
        HttpContentType httpContentType = HttpContentType.createHttpContentType();
        String extension = HeaderParser.getUrlExtension(url);
        String contentType = httpContentType.getContentType(extension);
        RequestMessageHeader requestMessageHeader = httpResponse.getHeader();
        byte[] body = IOUtils.getContent(url, extension);
        try {
            if (body != null) {
                dos.writeBytes(requestMessageHeader.response200Header(body.length, contentType));
                getResponseBody(body);
                return;
            }
            dos.writeBytes(requestMessageHeader.response404Header());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("HttpResponse forward end");
    }



    private void getResponseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
