package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {



    private final DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    HttpHeader httpHeader;

    public HttpResponse(DataOutputStream dos) {
        logger.info("HttpServletResponse Create with dos");
        this.dos = dos;
    }

    public void sendRedirect(String redirectUrl) {

        logger.info("HttpResponse redirect");
        //response302Header();
        //responseBody();
    }

    private byte[] getContent(String url) {
        byte[] body;
        httpHeader = new HttpHeader();

        String resourceUrl = httpHeader.getResourceUrl(url);

        try {
            File file = new File(resourceUrl);
            if(file.isFile()) {
                body = Files.readAllBytes(file.toPath());
                return body;
            }
            dos.writeBytes(httpHeader.response404Header());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void forward(String url) {
        logger.info("HttpResponse forward");
        byte[] body = getContent(url);
        if(body != null) {
            try {
                dos.writeBytes(httpHeader.response200Header(body.length));
                responseBody(body);
            } catch(IOException e) {
                logger.error(e.getMessage());
            }
        }
    }


    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
