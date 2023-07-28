package utils;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private final DataOutputStream dos;

    public FileUtils(DataOutputStream dos) {
        this.dos = dos;
    }

    public void writeHttpResponse(HttpResponse httpResponse) {
        try {
            byte[] body = httpResponse.getBody();
            dos.writeBytes(httpResponse.writeHttpResponse());
            if (body.length != 0) {
                dos.write(body, 0, body.length);
                dos.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
