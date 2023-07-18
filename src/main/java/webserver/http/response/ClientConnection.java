package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.HeaderParser;

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
        ResponseMessageHeader responseMessageHeader = httpResponse.getHeader();
        try {
            writeHeader(responseMessageHeader.response302Header(redirectUrl));
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("HttpResponse redirect end");
    }

    public void forward(String url) {
        HttpContentType httpContentType = HttpContentType.createHttpContentType();
        String extension = HeaderParser.getUrlExtension(url);
        String contentType = httpContentType.getContentType(extension);
        ResponseMessageHeader responseMessageHeader = httpResponse.getHeader();
        ResponseBody responseBody = httpResponse.getBody();
        try {
            if (responseBody.readBody() != null) {
                write200Response(contentType, responseMessageHeader, responseBody);
                dos.flush();
                return;
            }
            writeHeader(responseMessageHeader.response404Header());
            dos.flush();
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("HttpResponse forward end");
    }

    private void write200Response(String contentType, ResponseMessageHeader responseMessageHeader, ResponseBody responseBody) throws IOException {
        writeHeader(responseMessageHeader.response200Header(responseBody.getLength(), contentType));
        writeBody(responseBody);

    }

    private void writeHeader(String responseMessageHeader) throws IOException {
        dos.writeBytes(responseMessageHeader);
    }


    private void writeBody(ResponseBody body) throws IOException {
            dos.write(body.readBody(), 0, body.getLength());
    }
}
